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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
        boolean isTrue = true;
        try
        {
            vo.setIsApprove(Integer.valueOf(vo.getIsApproveName()));
            vo.setAccountType(Integer.valueOf(vo.getAccountTypeName()));
            Account account = makeVO2Account(vo, null);
            accountDao.save(account);
        } catch (Exception e)
        {
            isTrue = false;
            e.printStackTrace();
            throw new RollbackException();
        }
        return isTrue;
    }

    /**
     * 修改账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean modifyAccount(AccountVO vo)
    {
        boolean isTrue = true;
        try
        {
            Long accountId = vo.getAccountId();
            Account account = accountDao.findOne(accountId);
            makeVO2Account(vo, account);
            accountDao.save(account);
        } catch (Exception e)
        {
            isTrue = false;
            e.printStackTrace();
            throw new RollbackException();
        }
        return isTrue;
    }

    /**
     * 删除账单
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean deleteAccount(Long accountId)
    {
        boolean isTrue = true;
        try
        {
            accountDao.delete(accountId);
        } catch (Exception e)
        {
            isTrue = false;
            e.printStackTrace();
            throw new RollbackException();
        }
        return isTrue;
    }


    /**
     * 文件上传
     *
     * @param request
     * @param response
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void fileUpload(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            String path = request.getSession().getServletContext().getRealPath("");
            factory.setRepository(new File(path));
            factory.setSizeThreshold(1024 * 1024);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> itemList = upload.parseRequest(request);
            long accountId = 0;
            OutputStream outputStream = null;
            InputStream inputStream = null;
            for (FileItem item : itemList)
            {
                if (!item.isFormField())
                {
                    String name = item.getName();
                    outputStream = new FileOutputStream(new File(path, name));
                    inputStream = item.getInputStream();
                    int length = 0;
                    byte[] byteArr = new byte[1024];
                    while ((length = inputStream.read(byteArr)) != -1)
                    {
                        outputStream.write(byteArr, 0, length);
                    }
                } else
                {
                    String fieldName = item.getFieldName();
                    if ("accountId".equals(fieldName))
                    {
                        accountId = Long.valueOf(item.getString());
                    }
                }
            }
            if (outputStream != null)
            {
                outputStream.close();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
            //更新数据库附件
            Account account = accountDao.findOne(accountId);
            account.setAccountAccessory("path");
            accountDao.save(account);
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RollbackException();
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
