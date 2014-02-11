package org.fire.cost.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.UserDao;
import org.fire.cost.entity.Account;
import org.fire.cost.entity.User;
import org.fire.cost.enums.AccountTypeEnum;
import org.fire.cost.enums.ApproveEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账单service实现类
 *
 * @author liutengfei
 */
@Service
public class AccountServiceImpl implements AccountService
{
    @Resource
    private UserService userService;

    @Resource
    private AccountDao accountDao;

    @Resource
    private UserDao userDao;

    /**
     * 根据过滤条件查询账单信息
     */
    public List<AccountVO> getAccountByFilter(AccountVO vo)
    {
        List<AccountVO> voList = new ArrayList<AccountVO>();
        try
        {
            List<Account> accountList = accountDao.getAccountByFilter(vo);
            for (Account account : accountList)
            {
                voList.add(makeAccount2VO(account));
            }
            return voList;
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 增加账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean addAccount(AccountVO vo)
    {
        try
        {
            vo.setIsApprove(Integer.valueOf(vo.getIsApproveName()));
            vo.setAccountType(Integer.valueOf(vo.getAccountTypeName()));
            Account account = makeVO2Account(vo, null);
            accountDao.save(account);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 修改账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean modifyAccount(AccountVO vo)
    {
        try
        {
            Long accountId = vo.getAccountId();
            Account account = accountDao.findOne(accountId);
            makeVO2Account(vo, account);
            accountDao.save(account);
            return true;
        } catch (Exception e)
        {

            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 删除账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean deleteAccount(Long accountId)
    {
        try
        {
            accountDao.delete(accountId);
            return true;
        } catch (Exception e)
        {
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
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean fileUpload(HttpServletRequest request, HttpServletResponse response)
    {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try
        {
            request.setCharacterEncoding("UTF-8");
            String path = request.getSession().getServletContext().getRealPath("") + File.separator + "image";
            //判断FileUpload文件夹是否存在，如果不存在，创建文件夹
            String fileUpLoadDirname = "FileUpload";
            File file = new File(path);
            File[] fileArr = file.listFiles();
            boolean isFileUploadExist = false;
            for (File f : fileArr)
            {
                if (f.isDirectory() && fileUpLoadDirname.equals(f.getName()))
                {
                    isFileUploadExist = true;
                    break;
                }
            }
            if (!isFileUploadExist)
            {
                new File(path + File.separator + fileUpLoadDirname).mkdir();
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            String filePath = path + File.separator + fileUpLoadDirname;
            factory.setRepository(new File(filePath));
            factory.setSizeThreshold(1024 * 1024);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> itemList = upload.parseRequest(request);
            long accountId = 0;
            String accessoryValue = null;
            String filePrefix = "image/" + fileUpLoadDirname + "/";
            for (FileItem item : itemList)
            {
                if (!item.isFormField())
                {
                    String itemName = item.getName();
                    String itemPrefix = itemName.substring(0, itemName.indexOf("."));
                    String itemPostfix = itemName.substring(itemName.indexOf("."), itemName.length());
                    String fileName = itemPrefix + "_" + System.currentTimeMillis() + itemPostfix;
                    outputStream = new FileOutputStream(new File(filePath, fileName));
                    inputStream = item.getInputStream();
                    int length = 0;
                    byte[] byteArr = new byte[1024];
                    while ((length = inputStream.read(byteArr)) != -1)
                    {
                        outputStream.write(byteArr, 0, length);
                    }
                    accessoryValue = accessoryValue == null ? "" + filePrefix + fileName : "," + filePrefix + fileName;
                } else
                {
                    String fieldName = item.getFieldName();
                    if ("accountId".equals(fieldName))
                    {
                        accountId = Long.valueOf(item.getString());
                    }
                }
            }
            //更新数据库
            if (accountId != 0)
            {
                Account account = accountDao.findOne(accountId);
                account.setAccountAccessory(accessoryValue);
                accountDao.save(account);
            }
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RollbackException();
        } finally
        {
            try
            {
                if (outputStream != null)
                {
                    outputStream.close();
                }
                if (inputStream != null)
                {
                    inputStream.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
                throw new RollbackException();
            }
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
    private Account makeVO2Account(AccountVO vo, Account account) throws ParseException
    {
        if (account == null)
        {
            account = new Account();
            account.setCreateUser(userService.getLoginUserName());
            account.setCreateTime(new Date());
        }
        User user = userDao.findOne(AuthenticationUtil.getLoginUserId());
        account.setUser(user);
        account.setAccountMoney(vo.getAccountMoney());
        account.setAccountTime(DateUtil.makeStr2Date(vo.getAccountTime(), false));
        account.setIsApprove(vo.getIsApprove());
        account.setAccountType(vo.getAccountType());
        account.setAccountAccessory(vo.getAccountAccessory());
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
    private AccountVO makeAccount2VO(Account account)
    {
        AccountVO vo = new AccountVO();
        vo.setAccountId(account.getAccountId());
        vo.setUserId(account.getUser().getUserId());
        vo.setUserName(account.getUser().getUserName());
        vo.setAccountMoney(account.getAccountMoney());
        vo.setAccountTime(DateUtil.makeDate2Str(account.getAccountTime(), false));
        Date approveTime = account.getApproveTime();
        if (approveTime != null)
            vo.setApproveTime(DateUtil.makeDate2Str(approveTime, true));
        vo.setIsApprove(account.getIsApprove());
        vo.setIsApproveName(ApproveEnum.getName(account.getIsApprove()));
        vo.setAccountType(account.getAccountType());
        vo.setAccountTypeName(AccountTypeEnum.getName(account.getAccountType()));
        vo.setAccountAccessory(account.getAccountAccessory());
        vo.setCreateUser(account.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(account.getCreateTime(), true));
        vo.setModifyUser(account.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(account.getCreateTime(), true));
        vo.setAccountRemark(account.getAccountRemark());
        return vo;
    }
}
