package org.fire.cost.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.Account;
import org.fire.cost.domain.User;
import org.fire.cost.enums.*;
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
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    @Override
    public List<TypeVo> getAccountClass() {
        AccountClassEnum[] typeEnums = AccountClassEnum.values();
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        TypeVo typeVo = new TypeVo();
        typeVo.setCode(0);
        typeVo.setName("全部");
        typeList.add(typeVo);
        for (AccountClassEnum typeEnum : typeEnums) {
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
     * @param accountClass
     * @return
     */
    @Override
    public List<TypeVo> getAccountType(int accountClass) {
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        if (accountClass == 1) {
            typeList = getCommonAccountType();
        } else if (accountClass == 2) {
            typeList = getInvestAccountType();
        }
        return typeList;
    }


    /**
     * 得到账单状态
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
     * 获取就算方式
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
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
     * 获取用户每种消费类型消费金额
     *
     * @return
     */
    @Override
    public Map<String, List<AccountVO>> getAccountGroupByTypeAndUser(String startTime, String endTime) {
        List<AccountVO> accountVOList = accountDao.getAccountGroupByTypeAndUser(startTime, endTime);
        Map<String, List<AccountVO>> accountVoListMap = new LinkedHashMap<String, List<AccountVO>>();
        setAccountDataGroupByTypeAndUser(accountVOList, accountVoListMap);
        setAccountDataIfTypeIsNull(accountVoListMap);
        return accountVoListMap;
    }

    /**
     * 设置账单数据
     *
     * @param accountVOList
     * @param accountVoListMap
     */
    private void setAccountDataGroupByTypeAndUser(List<AccountVO> accountVOList, Map<String, List<AccountVO>> accountVoListMap) {
        AccountTypeEnum[] typeEnums = AccountTypeEnum.values();
        for (AccountVO accountVO : accountVOList) {
            List<AccountVO> voList = new ArrayList<AccountVO>();
            String userName = accountVO.getUserName();
            if (accountVoListMap.containsKey(userName)) {
                voList = accountVoListMap.get(userName);
            }
            Integer accountType = accountVO.getAccountType();
            for (AccountTypeEnum typeEnum : typeEnums) {
                int code = typeEnum.getCode();
                if (accountType != null && accountType == code) {
                    voList.add(accountVO);
                }
            }
            accountVoListMap.put(userName, voList);
        }
    }

    /**
     * 如果用户对应的消费类型不存在，需把对应的消费类型金额设置为0
     *
     * @param accountVoListMap
     */
    private void setAccountDataIfTypeIsNull(Map<String, List<AccountVO>> accountVoListMap) {
        AccountTypeEnum[] typeEnums = AccountTypeEnum.values();
        Set<String> userNameSet = accountVoListMap.keySet();
        for (String userName : userNameSet) {
            List<AccountVO> voList = accountVoListMap.get(userName);
            for (AccountTypeEnum typeEnum : typeEnums) {
                int code = typeEnum.getCode();
                boolean isContain = false;
                for (AccountVO accountVO : voList) {
                    Integer accountType = accountVO.getAccountType();
                    if (accountType != null && accountType == code) {
                        isContain = true;
                    }
                }
                if (!isContain) {
                    AccountVO accountVO = new AccountVO();
                    accountVO.setAccountType(code);
                    accountVO.setAccountMoney(BigDecimal.ZERO);
                    voList.add(accountVO);
                }
                Collections.sort(voList, new Comparator<AccountVO>() {
                    @Override
                    public int compare(AccountVO o1, AccountVO o2) {
                        return o1.getAccountType() - o2.getAccountType();
                    }
                });
            }
        }
    }

    /**
     * 得到itemList
     *
     * @param request
     * @param upLoadDirPath
     * @return
     * @throws FileUploadException
     */
    private List<FileItem> getFileItemList(HttpServletRequest request, String upLoadDirPath) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(upLoadDirPath));
        factory.setSizeThreshold(1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        return upload.parseRequest(request);
    }

    /**
     * 得到账单ID
     *
     * @param itemList
     * @return
     */
    private long getAccountId(List<FileItem> itemList) {
        long accountId = 0;
        for (FileItem item : itemList) {
            String fieldName = item.getFieldName();
            if (item.isFormField() && "accountId".equals(fieldName)) {
                accountId = Long.valueOf(item.getString());
            }
        }
        return accountId;
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
        cell.setCellValue("消费时间");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("状态");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("创建人");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("修改时间");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("修改人");
        cell.setCellStyle(style);
        cell.setCellStyle(style);
        cell = row.createCell(9);
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
            cell.setCellValue(vo.getAccountTime());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            Integer accountStatus = vo.getAccountStatus();
            String accountStatusName = AccountStatusEnum.getName(accountStatus);
            cell.setCellValue(accountStatusName);
            cell = row.createCell(5);
            cell.setCellValue(vo.getCreateTime());
            cell = row.createCell(6);
            cell.setCellValue(vo.getCreateUser());
            cell = row.createCell(7);
            cell.setCellValue(vo.getModifyUser());
            cell = row.createCell(8);
            cell.setCellValue(vo.getModifyTime());
            cell = row.createCell(9);
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
        account.setAccountMoney(vo.getAccountMoney());
        account.setAccountTime(DateUtil.makeStr2Date(vo.getAccountTime(), false));
        account.setAccountStatus(vo.getAccountStatus());
        account.setAccountClass(vo.getAccountClass());
        account.setAccountType(vo.getAccountType());
        account.setModifyUser(AuthenticationUtil.getUserName());
        account.setModifyTime(new Date());
        account.setAccountRemark(vo.getAccountRemark());
        return account;
    }

    /**
     * 获取日常类型
     *
     * @return
     */
    private List<TypeVo> getCommonAccountType() {
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        AccountTypeEnum[] typeEnums = AccountTypeEnum.values();
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
     * 获取投资类型
     *
     * @return
     */
    private List<TypeVo> getInvestAccountType() {
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        InvestEnum[] typeEnums = InvestEnum.values();
        TypeVo typeVo = new TypeVo();
        typeVo.setCode(0);
        typeVo.setName("全部");
        typeList.add(typeVo);
        for (InvestEnum typeEnum : typeEnums) {
            TypeVo vo = new TypeVo();
            vo.setCode(typeEnum.getCode());
            vo.setName(typeEnum.getName());
            typeList.add(vo);
        }
        return typeList;
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
        Integer accountStatus = account.getAccountStatus();
        vo.setAccountStatus(accountStatus);
        String accountStatusName = AccountStatusEnum.getName(accountStatus);
        vo.setAccountStatusName(accountStatusName);
        Integer accountType = account.getAccountType();
        vo.setAccountType(accountType);
        String accountTypeName = null;
        Integer accountClass = account.getAccountClass();
        vo.setAccountClass(accountClass);
        vo.setAccountClassName(AccountClassEnum.getName(accountClass));
        if (accountClass == 1) {
            accountTypeName = AccountTypeEnum.getName(accountType);
        } else if (accountClass == 2) {
            accountTypeName = InvestEnum.getName(accountType);
        }
        vo.setAccountTypeName(accountTypeName);
        vo.setCreateUser(account.getCreateUser());
        String createTime = DateUtil.makeDate2Str(account.getCreateTime(), true);
        vo.setCreateTime(createTime);
        vo.setModifyUser(account.getModifyUser());
        String modifyTime = DateUtil.makeDate2Str(account.getModifyTime(), true);
        vo.setModifyTime(modifyTime);
        vo.setAccountRemark(account.getAccountRemark());
        return vo;
    }
}
