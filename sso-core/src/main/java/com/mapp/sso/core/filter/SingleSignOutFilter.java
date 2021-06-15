package com.mapp.sso.core.filter;

import com.mapp.sso.core.session.SingleSignOutHandler;
import com.mapp.sso.core.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 单点退出拦截器
 *
 * @author mapp
 */
public class SingleSignOutFilter extends AbstractSSOFilter {

    private SingleSignOutHandler handler = new SingleSignOutHandler();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = WebUtil.toHttp(servletRequest);
        if (isIgnore(request.getServletPath())) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            if (WebUtil.isTokenRequest(request)) {
                String ticket = WebUtil.getCleanParam(request, "ticket");
                handler.addSession(ticket, request.getSession());
            }else if (WebUtil.isLogoutRequest(request)) {
                String ticket = WebUtil.getCleanParam(request, "logoutRequest");
                handler.removeSession(ticket);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }


}
