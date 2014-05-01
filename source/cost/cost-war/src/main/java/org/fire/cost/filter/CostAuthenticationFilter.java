package org.fire.cost.filter;

import net.rubyeye.xmemcached.exception.MemcachedException;
import org.fire.cost.context.ThreadMessageContext;
import org.fire.cost.context.UserContext;
import org.fire.cost.service.CostContextService;
import org.fire.cost.util.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 认证过滤
 *
 * @author liutengfei
 */
public class CostAuthenticationFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CostAuthenticationFilter.class);

    @Resource
    private CostContextService costContextService;

    /**
     * 登出路径
     */
    @Value("${cost.logout.ptah}")
    private String logoutPath;

    /**
     * 初始化
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 请求验证
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String userName = (String) session.getAttribute("userName");
        if (userName == null || userName.length() == 0) {
            session.setAttribute("userName", AuthenticationUtil.getUserName());
        }
        String sessionId = getCookieValue(httpRequest, "sessionId");
        if (sessionId != null && sessionId.trim().length() > 0) {
            UserContext userContext = costContextService.getUserContext(sessionId);
            if (userContext != null) {
                ThreadMessageContext.set(userContext);
                try {
                    costContextService.delay(sessionId);
                    chain.doFilter(httpRequest, httpResponse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (MemcachedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("缓存不存在或者失效，请重新登录");
                }

                httpResponse.sendRedirect(logoutPath);
                return;
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("请求信息不全，无效，请检查http头信息");
            }
            httpResponse.sendRedirect(logoutPath);
            return;
        }
    }

    public void destroy() {

    }

    /**
     * 得到cookie的值
     *
     * @return
     */
    private String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookieArr = request.getCookies();
        if (cookieArr != null && cookieArr.length != 0) {
            for (Cookie cookie : cookieArr) {
                if ("sessionId".equals(cookie.getName()) && "sessionId".equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
