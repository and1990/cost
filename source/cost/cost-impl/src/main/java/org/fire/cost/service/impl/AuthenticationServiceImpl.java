package org.fire.cost.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.fire.cost.context.UserContext;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.User;
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
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
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
     * 建立用户信息上下文
     */
    @Override
    public UserContext buildUserContext(String loginName) {
        UserContext userContext = buildContext(loginName);
        try {
            String json = jsonMapper.writeValueAsString(userContext);
            if (log.isDebugEnabled()) {
                log.debug("用户上下文信息\n" + json);
            }
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
