package com.mapp.sso.server.interceptor;

import com.mapp.sso.core.util.CookieUtil;
import com.mapp.sso.core.util.WebUtil;
import com.mapp.sso.server.ticket.TgtTicket;
import com.mapp.sso.server.ticket.TicketRegistry;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * sso session拦截器
 *
 * @author mapp
 */
public class SSOSessionInterceptor implements HandlerInterceptor {

    private TicketRegistry ticketRegistry;

    public SSOSessionInterceptor(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userInfo") != null) {
            return true;
        }else {
            TgtTicket ticket = ticketRegistry.getTicket(CookieUtil.getCookieValue(request, "SSO-TGC"), TgtTicket.class);
            if (ticket != null) {
                request.getSession().setAttribute("userInfo", ticket.getPrincipal());
                return true;
            }else {
                WebUtil.redirectToLogin(request, response);
            }
            return false;
        }
    }
}
