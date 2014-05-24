package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.GroupDao;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.Account;
import org.fire.cost.domain.Group;
import org.fire.cost.domain.User;
import org.fire.cost.enums.AccountStatusEnum;
import org.fire.cost.enums.AccountTypeEnum;
import org.fire.cost.enums.ClearTypeEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 账单service实现类
 *
 * @author liutengfei
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private UserService userService;

    @Resource
    private AccountDao accountDao;

    @Resource
    private UserDao userDao;

    @Resource
    private GroupDao groupDao;
    private Map<String, List<AccountVO>> accountDataIfMonthIsNull;

    /**
     * 根据过滤条件查询账单信息
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public List<AccountVO> getAccountByFilter(AccountVO vo, PageData<AccountVO> pageData) {
        List<AccountVO> voList = new ArrayList<AccountVO>();
        try {
            List<Account> accountList = accountDao.getAccountByFilter(vo, pageData);
            for (Account account : accountList) {
                AccountVO accountVO = makeAccount2VO(account);
                Group group = account.getGroup();
                accountVO.setGroupId(group.getGroupId());
                accountVO.setGroupName(group.getGroupName());
                voList.add(accountVO);
            }
            return voList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 得到账单数据总数
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public int getAccountTotal(AccountVO vo) {
        int total = accountDao.getAccountTotal(vo);
        return total;
    }

    /**
     * 增加账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean addAccount(AccountVO vo) {
        try {
            vo.setAccountStatus(AccountStatusEnum.Not_Approve.getCode());
            Account account = makeVO2Account(vo, null);
            accountDao.save(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 修改账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean modifyAccount(AccountVO vo) {
        try {
            Long accountId = vo.getAccountId();
            Account account = accountDao.findOne(accountId);
            makeVO2Account(vo, account);
            accountDao.save(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 删除账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean deleteAccount(Long accountId) {
        try {
            accountDao.delete(accountId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 审批账单
     *
     * @param accountIds
     * @param accountStatus
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void approveAccount(String accountIds, Integer accountStatus) {
        try {
            if (accountIds == null || accountIds.trim().length() == 0) {
                return;
            }
            List<Account> accountList = new ArrayList<Account>();
            String[] accountIdArr = accountIds.split(",");
            for (String accountId : accountIdArr) {
                Account account = accountDao.findOne(Long.valueOf(accountId));
                Integer status = account.getAccountStatus();
                if (account != null && status == AccountStatusEnum.Not_Approve.getCode()) {
                    account.setAccountStatus(accountStatus);
                    accountList.add(account);
                }
            }
            accountDao.save(accountList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 结算账单
     *
     * @param accountIds
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void clearAccount(String accountIds) {
        try {
            if (accountIds == null || accountIds.trim().length() == 0) {
                return;
            }
            List<Account> accountList = new ArrayList<Account>();
            String[] accountIdArr = accountIds.split(",");
            for (String accountId : accountIdArr) {
                Account account = accountDao.findOne(Long.valueOf(accountId));
                Integer status = account.getAccountStatus();
                if (account != null && status == AccountStatusEnum.Approve.getCode()) {
                    account.setAccountStatus(AccountStatusEnum.Clear.getCode());
                    accountList.add(account);
                }
            }
            accountDao.save(accountList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 得到账单类型
     *
     * @return
     */
    @Override
    public List<TypeVo> getAccountType() {
        AccountTypeEnum[] typeEnums = AccountTypeEnum.values();
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        TypeVo typeVo = new TypeVo();
        typeVo.setCode(0);
        typeVo.setName("全部");
        typeList.add(typeVo);
        for (AccountTypeEnum typeEnum : typeEnums) {
            TypeVo vo = new TypeVo();
            vo.setCode(typeEnum.getCode());
            vo.setName(typeEnum.getName());
            typeList.add(vo);
        }
        return typeList;
    }

    /**
     * 得到账单类型
     *
     * @return
     */
    @Override
    public List<TypeVo> getAccountStatus() {
        AccountStatusEnum[] typeEnums = AccountStatusEnum.values();
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        TypeVo typeVo = new TypeVo();
        typeVo.setCode(0);
        typeVo.setName("全部");
        typeList.add(typeVo);
        for (AccountStatusEnum typeEnum : typeEnums) {
            TypeVo vo = new TypeVo();
            vo.setCode(typeEnum.getCode());
            vo.setName(typeEnum.getName());
            typeList.add(vo);
        }
        return typeList;
    }

    /**
     * 获取excel数据
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public HSSFWorkbook getExcelData() {
        HSSFWorkbook hwb = new HSSFWorkbook();
        // 加边框
        HSSFCellStyle style = hwb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 列宽
        HSSFSheet sheet = hwb.createSheet("用户");
        for (int i = 0; i < 6; i++) {
            sheet.setColumnWidth(i, (short) 5000);
        }
        HSSFRow row = null;
        HSSFCell cell = null;
        createExcelTitle(sheet, style, row, cell);
        createExcelBody(sheet, style, row, cell);
        return hwb;
    }

    /**
     * 获取结算方式
     *
     * @return
     */
    @Override
    public List<TypeVo> getClearType() {
        ClearTypeEnum[] typeEnums = ClearTypeEnum.values();
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        for (ClearTypeEnum typeEnum : typeEnums) {
            TypeVo vo = new TypeVo();
            vo.setCode(typeEnum.getCode());
            vo.setName(typeEnum.getName());
            typeList.add(vo);
        }
        return typeList;
    }

    /**
     * 查找消费类型对应的账单数据
     *
     * @param accountStartTime 消费开始时间
     * @param accountEndTime   消费结束时间
     * @return
     */
    @Override
    public List<AccountVO> getAccountGroupByAccountType(String accountStartTime, String accountEndTime) {
        List<AccountVO> accountVOList = accountDao.getAccountGroupByAccountType(accountStartTime, accountEndTime);
        return accountVOList;
    }

    /**
     * 查找用户对应的账单数据
     *
     * @param accountStartTime 消费开始时间
     * @param accountEndTime   消费结束时间
     * @return
     */
    @Override
    public List<AccountVO> getAccountGroupByUser(String accountStartTime, String accountEndTime) {
        List<AccountVO> accountVOList = accountDao.getAccountGroupByUser(accountStartTime, accountEndTime);
        return accountVOList;
    }

    /**
     * 获取每月每个用户消费金额
     *
     * @param year
     * @return
     */
    public Map<String, List<AccountVO>> getAccountGroupByMonthAndUser(int year) {
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        List<AccountVO> accountVOList = accountDao.getAccountGroupByMonthAndUser(startTime, endTime);
        Map<String, List<AccountVO>> accountVoListMap = new LinkedHashMap<String, List<AccountVO>>();
        setAccountGroupByMonthAndUser(accountVOList, accountVoListMap);
        Map<String, List<AccountVO>> listMap = getAccountDataIfUserIsNull(accountVoListMap);
        return listMap;
    }

    /**
     * 设置每月每个用户对应的消费金额
     *
     * @param accountVOList
     * @param accountVoListMap
     */
    private void setAccountGroupByMonthAndUser(List<AccountVO> accountVOList, Map<String, List<AccountVO>> accountVoListMap) {
        List<User> userList = userDao.findAll();
        for (AccountVO accountVO : accountVOList) {
            List<AccountVO> voList = new ArrayList<AccountVO>();
            String month = accountVO.getMonth();
            if (accountVoListMap.containsKey(month)) {
                voList = accountVoListMap.get(month);
            }
            Long userId = accountVO.getUserId();
            for (User user : userList) {
                Long id = user.getUserId();
                if (userId != null && userId.toString().equals(id.toString())) {
                    voList.add(accountVO);
                }
            }
            accountVoListMap.put(month, voList);
        }
    }

    /**
     * 如果当前月份的用户没有消费记录，需把当前用户的消费金额设置为0
     *
     * @param accountVoListMap
     */
    private Map<String, List<AccountVO>> getAccountDataIfUserIsNull(Map<String, List<AccountVO>> accountVoListMap) {
        List<User> userList = userDao.findAll();
        //如果当前月份有的用户没有进行消费， 需把用户的金额设置为0
        Set<String> monthSet = accountVoListMap.keySet();
        for (String month : monthSet) {
            List<AccountVO> voList = accountVoListMap.get(month);
            for (User user : userList) {
                Long userId = user.getUserId();
                boolean isContain = false;
                for (AccountVO accountVO : voList) {
                    Long id = accountVO.getUserId();
                    if (userId != null && id != null && userId.toString().equals(id.toString())) {
                        isContain = true;
                    }
                }
                if (!isContain) {
                    AccountVO accountVO = new AccountVO();
                    accountVO.setUserId(userId);
                    accountVO.setAccountMoney(BigDecimal.ZERO);
                    accountVO.setMonth(month);
                    voList.add(accountVO);
                }
            }
        }
        //如果当前月份没有消费，需把当前月份金额设置为0
        Map<String, List<AccountVO>> listMap = new HashMap<String, List<AccountVO>>();
        String[] monthArr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        for (String month : monthArr) {
            if (accountVoListMap.containsKey(month)) {
                continue;
            }
            List<AccountVO> voList = new ArrayList<AccountVO>();
            for (User user : userList) {
                AccountVO accountVO = new AccountVO();
                accountVO.setUserId(user.getUserId());
                accountVO.setAccountMoney(BigDecimal.ZERO);
                accountVO.setMonth(month);
                voList.add(accountVO);
            }
            listMap.put(month, voList);
        }
        accountVoListMap.putAll(listMap);

        //排序
        Map<String, List<AccountVO>> map = new LinkedHashMap<String, List<AccountVO>>();
        for (String month : monthArr) {
            List<AccountVO> accountVOList = accountVoListMap.get(month);
            map.put(month, accountVOList);
        }
        return map;
    }


    /**
     * 创建表头
     *
     * @param sheet
     * @param row
     * @param cell
     */
    private void createExcelTitle(HSSFSheet sheet, HSSFCellStyle style, HSSFRow row, HSSFCell cell) {
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("金额");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("消费类型");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("结算方式");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("消费组");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("消费时间");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("状态");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("创建人");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("修改人");
        cell.setCellStyle(style);
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
    }

    /**
     * 创建表体数据
     */
    private void createExcelBody(HSSFSheet sheet, HSSFCellStyle style, HSSFRow row, HSSFCell cell) {
        int rowIndex = 1;
        AccountVO accountVO = new AccountVO();
        accountVO.setPage(false);
        List<AccountVO> accountVOList = getAccountByFilter(accountVO, new PageData<AccountVO>());
        for (AccountVO vo : accountVOList) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(vo.getUserName());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(vo.getAccountMoney().toString());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            Integer accountType = vo.getAccountType();
            String accountTypeName = AccountTypeEnum.getName(accountType);
            cell.setCellValue(accountTypeName);
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(vo.getGroupName());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(vo.getAccountTime());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            Integer accountStatus = vo.getAccountStatus();
            String accountStatusName = AccountStatusEnum.getName(accountStatus);
            cell.setCellValue(accountStatusName);
            cell = row.createCell(6);
            cell.setCellValue(vo.getCreateTime());
            cell = row.createCell(7);
            cell.setCellValue(vo.getCreateUser());
            cell = row.createCell(8);
            cell.setCellValue(vo.getModifyUser());
            cell = row.createCell(9);
            cell.setCellValue(vo.getModifyTime());
            cell = row.createCell(10);
            cell.setCellValue(vo.getAccountRemark());
            cell.setCellStyle(style);
            rowIndex++;
        }
    }

    /**
     * 将VO对象转换成实体对象
     *
     * @param account
     * @param account
     * @return
     * @throws ParseException
     */

    private Account makeVO2Account(AccountVO vo, Account account) throws ParseException {
        if (account == null) {
            account = new Account();
            account.setCreateUser(AuthenticationUtil.getUserName());
            account.setCreateTime(new Date());
        }
        User user = userDao.findOne(AuthenticationUtil.getLoginUserId());
        account.setUser(user);
        Group group = groupDao.findOne(vo.getGroupId());
        account.setGroup(group);
        account.setAccountMoney(vo.getAccountMoney());
        account.setAccountTime(DateUtil.makeStr2Date(vo.getAccountTime(), false));
        account.setAccountStatus(vo.getAccountStatus());
        account.setAccountType(vo.getAccountType());
        account.setAccountFile(vo.getAccountFile());
        account.setModifyUser(AuthenticationUtil.getUserName());
        account.setModifyTime(new Date());
        account.setAccountRemark(vo.getAccountRemark());
        return account;
    }

    /**
     * 将实体对象转换成vo
     *
     * @param account
     * @return
     */
    private AccountVO makeAccount2VO(Account account) {
        AccountVO vo = new AccountVO();
        vo.setAccountId(account.getAccountId());
        vo.setUserId(account.getUser().getUserId());
        vo.setUserName(account.getUser().getUserName());
        vo.setAccountMoney(account.getAccountMoney());
        vo.setAccountTime(DateUtil.makeDate2Str(account.getAccountTime(), false));
        vo.setAccountStatus(account.getAccountStatus());
        vo.setAccountStatusName(AccountStatusEnum.getName(account.getAccountStatus()));
        vo.setAccountType(account.getAccountType());
        vo.setAccountTypeName(AccountTypeEnum.getName(account.getAccountType()));
        vo.setAccountFile(account.getAccountFile());
        vo.setCreateUser(account.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(account.getCreateTime(), true));
        vo.setModifyUser(account.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(account.getCreateTime(), true));
        vo.setAccountRemark(account.getAccountRemark());
        return vo;
    }

}
