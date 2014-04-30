package org.fire.cost.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fire.cost.dao.AccountDao;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 文件上传
     *
     * @param request
     * @param response
     */
    @Override
    public boolean fileUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String path = request.getSession().getServletContext().getRealPath("") + File.separator + "image";
            String upLoadDirPath = getUpLoadDirPath(path);
            List<FileItem> itemList = getFileItemList(request, upLoadDirPath);
            long accountId = getAccountId(itemList);
            String accessoryValue = accountDao.findOne(accountId).getAccountFile();
            for (FileItem item : itemList) {
                if (!item.isFormField()) {
                    String itemName = item.getName();
                    String itemPrefix = itemName.substring(0, itemName.indexOf("."));
                    String itemPostfix = itemName.substring(itemName.indexOf("."), itemName.length());
                    String fileName = itemPrefix + "_" + System.currentTimeMillis() + itemPostfix;
                    item.write(new File(upLoadDirPath, fileName));
                    String finalFileName = request.getContextPath() + "/image/FileUpload/" + fileName;
                    accessoryValue = accessoryValue == null ? finalFileName : accessoryValue + "," + finalFileName;
                }
            }
            //更新数据库
            updateAccountFile(accountId, accessoryValue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 审批账单
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void approveAccount() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 结算账单
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void clearAccount() {
        try {

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
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public List<TypeVo> getAccountType() {
        AccountTypeEnum[] typeEnums = AccountTypeEnum.values();
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        for (AccountTypeEnum typeEnum : typeEnums) {
            TypeVo vo = new TypeVo();
            vo.setCode(typeEnum.getCode());
            vo.setName(typeEnum.getName());
            typeList.add(vo);
        }
        return typeList;
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
     * 更新账单附件
     *
     * @param accountId
     * @param accessoryValue
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    private void updateAccountFile(long accountId, String accessoryValue) {
        if (accountId == 0) {
            return;
        }
        Account account = accountDao.findOne(accountId);
        account.setAccountFile(accessoryValue);
        accountDao.save(account);
    }


    /**
     * 生成文件上传到的文件夹
     *
     * @param path
     * @return
     */
    private String getUpLoadDirPath(String path) {
        final String fileUpLoadDirname = "FileUpload";
        File[] fileArr = new File(path).listFiles();
        boolean isFileUploadExist = false;
        for (File f : fileArr) {
            if (f.isDirectory() && fileUpLoadDirname.equals(f.getName())) {
                isFileUploadExist = true;
                break;
            }
        }
        if (!isFileUploadExist) {
            new File(path + File.separator + fileUpLoadDirname).mkdir();
        }
        return path + File.separator + fileUpLoadDirname;
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
            account.setCreateUser(userService.getLoginUserName());
            account.setCreateTime(new Date());
        }
        User user = userDao.findOne(AuthenticationUtil.getLoginUserId());
        account.setUser(user);
        account.setAccountMoney(vo.getAccountMoney());
        account.setAccountTime(DateUtil.makeStr2Date(vo.getAccountTime(), false));
        account.setAccountStatus(vo.getAccountStatus());
        account.setAccountType(vo.getAccountType());
        account.setClearType(vo.getClearType());
        account.setAccountFile(vo.getAccountFile());
        account.setModifyUser(userService.getLoginUserName());
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
        vo.setClearType(account.getClearType());
        vo.setClearTypeName(ClearTypeEnum.getName(account.getClearType()));
        vo.setAccountFile(account.getAccountFile());
        vo.setCreateUser(account.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(account.getCreateTime(), true));
        vo.setModifyUser(account.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(account.getCreateTime(), true));
        vo.setAccountRemark(account.getAccountRemark());
        return vo;
    }
}
