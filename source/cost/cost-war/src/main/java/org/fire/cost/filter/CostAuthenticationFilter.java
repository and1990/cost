package org.fire.cost.filter;

import net.rubyeye.xmemcached.exception.MemcachedException;
import org.fire.cost.context.ThreadMessageContext;
import org.fire.cost.context.UserContext;
import org.fire.cost.service.CostContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 认证过滤
 *
 * @author liutengfei
 */
public class CostAuthenticationFilter implements Filter
{
    private static final Logger log = LoggerFactory.getLogger(CostAuthenticationFilter.class);

    @Resource
    private CostContextService costContextService;

    /**
     * 登出路径
     */
    @Value("${cost.loginout.ptah}")
    private String loginOutPath;

    /**
     * 心跳检查
     */
    @Value("$(cost.palpitation.path)")
    private String palpitationPath;

    /**
     * 初始化
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // TODO Auto-generated method stub
    }

    /**
     * 请求验证
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Cookie[] cookieArr = httpRequest.getCookies();
        String sessionId = getCookieValue(cookieArr, "sessionId");
        if (sessionId != null && sessionId.trim().length() > 0)
        {
            UserContext cache = costContextService.getUserContext(sessionId);
            if (cache != null)
            {
                UserContext userContext = new UserContext();
                String userId = getCookieValue(cookieArr, "userId");
                String uuid = getCookieValue(cookieArr, "uuid");
                userContext.setSessionId(sessionId);
                userContext.setUserId(Long.valueOf(userId));
                userContext.setUuid(uuid);
                userContext.setTimeStamp(cache.getTimeStamp());
                userContext.setUserType(cache.getUserType());
                ThreadMessageContext.set(userContext);
                try
                {
                    int path = httpRequest.getRequestURI().lastIndexOf(palpitationPath);
                    //业务请求 延迟会话
                    if (path < 0)
                    {
                        costContextService.delay(sessionId);
                    }
                    chain.doFilter(request, response);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } catch (MemcachedException e)
                {
                    e.printStackTrace();
                } catch (TimeoutException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("缓存不存在或者失效，请重新登录");
                }
                request.getRequestDispatcher(loginOutPath).forward(request, response);
                return;
            }
        } else
        {
            if (log.isDebugEnabled())
            {
                log.debug("请求头信息不全，无效，请检查http头信息");
            }
            request.getRequestDispatcher(loginOutPath).forward(request, response);
            return;
        }
    }

    public void destroy()
    {
        // TODO Auto-generated method stub

    }

    /**
     * 得到cookie的值
     *
     * @param cookieArr
     * @return
     */
    private String getCookieValue(Cookie[] cookieArr, String key)
    {
        if (cookieArr == null || cookieArr.length == 0)
        {
            return null;
        }
        for (Cookie cookie : cookieArr)
        {
            if ("sessionId".equals(cookie.getName()) && "sessionId".equals(key))
            {
                return cookie.getValue();
            } else if ("uuid".equals(cookie.getName()) && "uuid".equals(key))
            {
                return cookie.getValue();
            } else if ("userId".equals(cookie.getName()) && "userId".equals(key))
            {
                return cookie.getValue();
            }
        }
        return null;
    }

}
