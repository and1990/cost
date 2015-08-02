package org.fire.cost.filter;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤
 *
 * @author liutengfei
 */
public class CostAuthenticationFilter implements Filter {

    /**
     * 登出路径
     */
    @Value("${cost.logout.path}")
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
        Object loginName = httpRequest.getSession().getAttribute("loginName");
        if (loginName != null) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect(logoutPath);
            return;
        }
    }

    public void destroy() {

    }
}
