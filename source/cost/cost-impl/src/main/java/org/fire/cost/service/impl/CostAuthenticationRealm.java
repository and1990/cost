package org.fire.cost.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.User;
import org.fire.cost.enums.StatusEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证信息实现
 *
 * @author liutengfei
 */
@Service(value = "costAuthenticationRealm")
public class CostAuthenticationRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String loginName = token.getUsername();
        User user = userDao.findByLoginName(loginName);
        String inputPassword = new String(token.getPassword());
        String md5Password = DigestUtils.md5Hex(inputPassword);
        String password = user.getPassword();
        if (user != null && password.equals(md5Password)) {
            if (user.getUserStatus() == StatusEnum.Enable.getCode()) {
                return new SimpleAuthenticationInfo(token.getPrincipal(), token.getPassword(), token.getUsername());
            } else {
                throw new AuthenticationException("COST:用户被禁用");
            }
        } else {
            throw new AuthenticationException("COST:用户名或密码错误");
        }

    }

    /**
     * 授权，设置用户角色
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object loginName = principals.getPrimaryPrincipal();
        if (loginName != null) {
            User user = userDao.findByLoginName(loginName.toString());
            String role = user.getUserType() == 1 ? "common" : "admin";
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole(role);
            return info;
        }
        return new SimpleAuthorizationInfo();
    }
}
