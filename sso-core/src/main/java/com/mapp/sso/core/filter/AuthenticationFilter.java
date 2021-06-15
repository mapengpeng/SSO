package com.mapp.sso.core.filter;


import cn.hutool.core.util.StrUtil;
import com.mapp.sso.core.authentication.Principal;
import com.mapp.sso.core.util.WebUtil;
import com.mapp.sso.core.validation.TicketValidator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * sso 子系统拦截器
 *
 * @author mapp
 */
public class AuthenticationFilter extends AbstractSSOFilter {

    private TicketValidator ticketValidator;

    public AuthenticationFilter() {
        this.ticketValidator = new TicketValidator();
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = WebUtil.toHttp(servletRequest);
        if (isIgnore(request.getServletPath())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = WebUtil.toHttp(servletResponse);
            HttpSession session = request.getSession(false);

            Principal principal = session == null ? null : (Principal) session.getAttribute("sso_attr");
            if (principal != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String ticket = WebUtil.getCleanParam(request, "ticket");
                // 校验ticket
                if (StrUtil.isNotBlank(ticket)) {
                    try {
                        Principal p = ticketValidator.validateTiet(ticket);
                        request.getSession().setAttribute("sso_attr", p);
                        WebUtil.sendRedirect(response, request.getContextPath() + "/");
                    } catch (Exception e) {
                        // 错误处理
                        WebUtil.redirectToSSOLogin(response);
                    }

                } else {
                    WebUtil.redirectToSSOLogin(response);
                }
            }
        }
    }

}
