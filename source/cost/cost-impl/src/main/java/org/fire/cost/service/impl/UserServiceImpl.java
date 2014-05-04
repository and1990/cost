package org.fire.cost.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.User;
import org.fire.cost.enums.UserStatusEnum;
import org.fire.cost.enums.UserTypeEnum;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    /**
     * @param name     用户名
     * @param password 密码
     */
    public boolean userLogin(String name, String password) {
        try {
            User user = userDao.findByLoginName(name);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
        return false;
    }

    /**
     * 更新用户登录时间
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean changeUserLoginTime(Long userId) {
        try {
            userDao.changeUserLoginTime(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 根据过滤条件查询用户记录
     */
    public List<UserVO> getUserByFilter(UserVO vo, PageData<UserVO> pageData) {
        List<UserVO> userVOList = new ArrayList<UserVO>();
        try {
            List<User> userList = userDao.getUserByFilter(vo, pageData);
            for (User user : userList) {
                UserVO userVO = makeUser2VO(user);
                userVOList.add(userVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userVOList;
    }

    /**
     * 获取用户总记录数
     *
     * @return
     */
    @Override
    public int getUserDataTotal(UserVO vo) {
        int total = userDao.getUserDataTotal(vo);
        return total;
    }

    /**
     * 增加用户
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean addUser(UserVO vo) {
        try {
            vo.setUserStatus(UserStatusEnum.Enable.getCode());
            vo.setUserType(UserTypeEnum.Common.getCode());
            User user = makeVO2User(vo, null);
            String password = vo.getPassword();
            user.setPassword(DigestUtils.md5Hex(password));
            userDao.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 修改用户信息
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean modifyUser(UserVO vo) {
        try {
            Long userId = vo.getUserId();
            User user = userDao.findOne(userId);
            makeVO2User(vo, user);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 删除用户
     *
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean deleteUser(Long userId) {
        try {
            userDao.delete(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 得到当前登录用户名字
     *
     * @return
     */
    public String getLoginUserName() {
        Long userId = AuthenticationUtil.getLoginUserId();
        if (userId != null && userId != 0) {
            User user = userDao.findByUserId(userId);
            if (user != null) {
                return user.getUserName();
            }
        }
        return null;
    }

    /**
     * 根据用户id查询用户
     *
     * @param userIds 用户id，格式：id1,id2,..idn
     * @return
     */
    public List<UserVO> getUsersByUserIds(String userIds) {
        if (userIds == null || userIds.trim().length() == 0) {
            throw new RuntimeException("数据格式不正确");
        }
        try {
            List<User> userList = userDao.getUsersByUserIds(userIds);
            List<UserVO> userVOList = new ArrayList<UserVO>();
            for (User user : userList) {
                userVOList.add(makeUser2VO(user));
            }
            return userVOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据用户id查询用户名称
     *
     * @param userIds 用户id
     * @return
     */
    public String getUserNamesByUserIds(String userIds) {
        String userNames = null;
        List<UserVO> userVOList = getUsersByUserIds(userIds);
        if (userVOList != null || userVOList.size() != 0) {
            for (UserVO userVO : userVOList) {
                String userName = userVO.getUserName();
                userNames = userNames == null ? userName : userNames + "," + userName;
            }
        }
        return userNames;
    }

    /**
     * 禁用用户
     *
     * @param userIds
     * @param userStatus
     */
    @Override
    public void modifyUserStatus(String userIds, Integer userStatus) {
        String[] userIdArr = userIds.trim().split(",");
        List<User> userList = new ArrayList<User>();
        for (String userId : userIdArr) {
            User user = userDao.findOne(Long.valueOf(userId));
            user.setUserStatus(userStatus);
            userList.add(user);
        }
        userDao.save(userList);
    }

    /**
     * 修改密码
     *
     * @return
     */
    @Override
    public void modifyPassword(String password) {
        try {
            Long userId = AuthenticationUtil.getLoginUserId();
            User user = userDao.findOne(userId);
            boolean passwordNotNull = password != null && password.trim().length() != 0;
            if (user != null && passwordNotNull) {
                user.setPassword(DigestUtils.md5Hex(password));
                userDao.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证密码是否正确
     *
     * @return
     */
    @Override
    public boolean validatePassword(String password) {
        try {
            Long userId = AuthenticationUtil.getLoginUserId();
            User user = userDao.findOne(userId);
            boolean passwordNotNull = password != null && password.trim().length() != 0;
            if (user != null && passwordNotNull) {
                String userPassword = user.getPassword();
                if (password.equals(userPassword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 导出用户到excel
     *
     * @return
     */
    @Override
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
        cell.setCellValue("登录名");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("年龄");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("地址");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("邮箱");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("用户类型");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("用户状态");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("创建人");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("修改人");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("修改时间");
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
        UserVO userVO = new UserVO();
        userVO.setPage(false);
        List<UserVO> userVOList = getUserByFilter(userVO, new PageData<UserVO>());
        for (UserVO vo : userVOList) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(vo.getUserName());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(vo.getLoginName());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(vo.getUserAge());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(vo.getUserAddress());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(vo.getUserEmail());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            Integer userType = vo.getUserType();
            String userTypeName = UserTypeEnum.getName(userType);
            cell.setCellValue(userTypeName);
            cell = row.createCell(6);
            Integer userStatus = vo.getUserStatus();
            String userStatusName = UserStatusEnum.getName(userStatus);
            cell.setCellValue(userStatusName);
            cell = row.createCell(7);
            cell.setCellValue(vo.getCreateUser());
            cell.setCellStyle(style);
            cell = row.createCell(8);
            cell.setCellValue(vo.getCreateTime());
            cell.setCellStyle(style);
            cell = row.createCell(9);
            cell.setCellValue(vo.getModifyUser());
            cell.setCellStyle(style);
            cell = row.createCell(10);
            cell.setCellValue(vo.getModifyTime());
            cell.setCellStyle(style);
            cell = row.createCell(11);
            cell.setCellValue(vo.getUserRemark());
            cell.setCellStyle(style);
            rowIndex++;
        }
    }

    /**
     * 将UserVO转换成User对象
     *
     * @param vo
     * @return
     */
    private User makeVO2User(UserVO vo, User user) {
        if (user == null) {
            user = new User();
            user.setLoginTime(new Date());
            user.setCreateUser(getLoginUserName());
            user.setCreateTime(new Date());
        } else {
            user.setUserId(vo.getUserId());
        }
        user.setUserName(vo.getUserName());
        user.setLoginName(vo.getLoginName());
        user.setUserAge(vo.getUserAge());
        user.setUserAddress(vo.getUserAddress());
        user.setUserEmail(vo.getUserEmail());
        user.setUserImage(vo.getUserImage());
        user.setUserStatus(vo.getUserStatus());
        user.setUserType(vo.getUserType());
        user.setUserRemark(vo.getUserRemark());
        user.setModifyUser(vo.getModifyUser());
        user.setModifyTime(new Date());
        return user;
    }

    /**
     * 将实体对象user装换成vo对象
     *
     * @param user
     * @return
     */
    private UserVO makeUser2VO(User user) {
        UserVO vo = new UserVO();
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setPassword(user.getPassword());
        vo.setLoginName(user.getLoginName());
        vo.setUserAge(user.getUserAge());
        vo.setUserAddress(user.getUserAddress());
        vo.setUserEmail(user.getUserEmail());
        vo.setUserImage(user.getUserImage());
        vo.setLoginTime(DateUtil.makeDate2Str(user.getLoginTime(), true));
        vo.setUserStatus(user.getUserStatus());
        vo.setUserStatusName(UserStatusEnum.getName(user.getUserStatus()));
        vo.setUserType(user.getUserType());
        vo.setUserTypeName(UserTypeEnum.getName(user.getUserType()));
        vo.setCreateUser(user.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(user.getCreateTime(), true));
        vo.setModifyUser(user.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(user.getModifyTime(), true));
        vo.setUserRemark(user.getUserRemark());
        return vo;
    }
}
