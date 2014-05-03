package org.fire.cost.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.codehaus.jackson.map.ObjectMapper;
import org.fire.cost.context.UserContext;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.User;
import org.fire.cost.enums.UserStatusEnum;
import org.fire.cost.service.AuthenticationService;
import org.fire.cost.service.CostContextService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * 认证信息实现
 *
 * @author liutengfei
 */
@Service(value = "authenticationService")
public class AuthenticationServiceImpl extends AuthenticatingRealm implements AuthenticationService {
    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(AuthenticationServiceImpl.class);
    /**
     * json 解析处理器
     */
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @Resource
    private UserDao userDao;

    @Resource
    private CostContextService costContextService;

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String loginName = token.getUsername();
        char[] chars = token.getPassword();
        User user = userDao.findByLoginName(loginName);
        String inputPassword = new String(chars);
        if (user != null && user.getPassword().equals(inputPassword)) {
            if (user.getUserStatus() == UserStatusEnum.Enable.getCode()) {
                return new SimpleAuthenticationInfo(token.getPrincipal(), token.getPassword(), token.getUsername());
            } else {
                throw new AuthenticationException("COST:用户被禁用");
            }
        } else {
            throw new AuthenticationException("COST:用户名或密码错误");
        }

    }

    /**
     * 建立用户信息上下文
     */
    public UserContext buildUserContext(String loginName) {
        UserContext userContext = buildContext(loginName);
        String json = null;
        try {
            json = jsonMapper.writeValueAsString(userContext);
            if (log.isDebugEnabled()) {
                log.debug("用户上下文信息\n" + json);
            }
            // 用户信息放入缓存
            costContextService.add(userContext.getSessionId(), json);
        } catch (IOException e1) {
            e1.printStackTrace();
            log.error("创建用户上下文信息失败");
        }

        return userContext;
    }

    /**
     * 建立用户上下文
     */
    private UserContext buildContext(String loginName) {
        UserContext userContext = new UserContext();
        User user = userDao.findByLoginName(loginName);
        Long userId = user.getUserId();
        userContext.setUserId(userId);
        userContext.setUserName(user.getUserName());
        userContext.setUuid(UUID.randomUUID().toString());
        userContext.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        StringBuffer sessionId = new StringBuffer();
        sessionId.append("ORG_FIRE_COST").append("_").append(userId);
        userContext.setSessionId(String.valueOf(sessionId));
        return userContext;
    }

}
