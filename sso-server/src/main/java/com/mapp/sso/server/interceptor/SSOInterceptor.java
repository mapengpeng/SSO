package com.mapp.sso.server.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mapp.sso.core.authentication.Principal;
import com.mapp.sso.core.authentication.SimplePrincipal;
import com.mapp.sso.core.util.CookieUtil;
import com.mapp.sso.core.util.WebUtil;
import com.mapp.sso.server.handler.AbstractAuthenticationHandler;
import com.mapp.sso.server.handler.ServiceCheckHandler;
import com.mapp.sso.server.manager.LogoutManager;
import com.mapp.sso.server.manager.ServiceManager;
import com.mapp.sso.server.service.WebApplicationService;
import com.mapp.sso.server.ticket.ServiceTicket;
import com.mapp.sso.server.ticket.TgtTicket;
import com.mapp.sso.server.ticket.TicketRegistry;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * sso 拦截器
 *
 * @author mapp
 */
public class SSOInterceptor implements HandlerInterceptor {

    private TicketRegistry ticketRegistry;
    private LogoutManager logoutManager;
    private ServiceManager serviceManager;
    private AbstractAuthenticationHandler authenticationHandler;
    private ServiceCheckHandler serviceCheckHandler = new ServiceCheckHandler();

    public SSOInterceptor(TicketRegistry ticketRegistry, LogoutManager logoutManager,
                          ServiceManager serviceManager, AbstractAuthenticationHandler authenticationHandler) {
        this.ticketRegistry = ticketRegistry;
        this.logoutManager = logoutManager;
        this.serviceManager = serviceManager;
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (isLoginRequest(request)) {
            if (isLoginSubmission(request)) {
                String tgtId = IdUtil.fastUUID();
                try {
                    String service = WebUtil.getCleanParam(request, "service");
                    // 登录验证
                    Principal authenticate = authenticationHandler.authenticate(request);
                    CookieUtil.addCookie(response, "SSO-TGC", "SSO-TGC-" + tgtId, 8 * 60 * 60);
                    TgtTicket tgtTicket = new TgtTicket("SSO-TGC-" + tgtId, 1800 * 1000L, authenticate);
                    ticketRegistry.addTicket(tgtTicket);
                    if (StrUtil.isNotBlank(service)) {
                        ServiceTicket serviceTicket = new ServiceTicket("st-" + IdUtil.fastUUID(), 2L, service);
                        serviceTicket.setTgtTicket(tgtTicket);
                        ticketRegistry.addTicket(serviceTicket);
                        serviceManager.addService(tgtTicket.getId(), new WebApplicationService(service, service, serviceTicket.getId()));
                        WebUtil.sendRedirect(response, service + "?ticket=" + serviceTicket.getId());
                        return false;
                    }
                } catch (Exception e) {
                    CookieUtil.addCookie(response, "SSO-TGC", "", 0);
                    ticketRegistry.deleteTicket(tgtId);
                    request.setAttribute("errMsg", "登录失败！");
                }
            } else {

                String service = WebUtil.getCleanParam(request, "service");
                // 检验
                if (StrUtil.isNotBlank(service) && !serviceCheckHandler.check(service)) {
                    WebUtil.writeJson(response, HttpServletResponse.SC_FORBIDDEN, "非法的子系统！");
                    return false;
                }
                String tgc = CookieUtil.getCookieValue(request, "SSO-TGC");

                if (StrUtil.isNotBlank(tgc)) {
                    // 说明已经登陆过，获取TGT
                    TgtTicket ticket = ticketRegistry.getTicket(tgc, TgtTicket.class);
                    // 判断是否失效
                    if (ticket != null && !ticket.validate()) {
                        if (StrUtil.isNotBlank(service)) {
                            // 重新生成ST
                            ServiceTicket serviceTicket = new ServiceTicket(IdUtil.fastUUID(), 2L, service);
                            serviceTicket.setTgtTicket(ticket);
                            ticketRegistry.addTicket(serviceTicket);
                            serviceManager.addService(ticket.getId(), new WebApplicationService(service, service, serviceTicket.getId()));
                            // 重定向到子系统
                            WebUtil.sendRedirect(response, service + "?ticket=" + serviceTicket.getId());
                            return false;
                        }else {
                            // 直接重定向到sso首页
                            WebUtil.sendRedirect(response, request.getContextPath() + "/");
                            return false;
                        }
                    }
                }
            }
        }// 单点退出
        if (isLogoutRequest(request)) {
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
            logoutManager.logout(CookieUtil.getCookieValue(request, "SSO-TGC"));
            CookieUtil.addCookie(response, "SSO-TGC", "", 0);
            WebUtil.redirectToLogin(request, response);
            return false;
        }
        return true;
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return "/login".equalsIgnoreCase(request.getServletPath());
    }

    private boolean isLogoutRequest(HttpServletRequest request) {
        return "/logout".equalsIgnoreCase(request.getServletPath());
    }

    private boolean isLoginSubmission(HttpServletRequest request) {
        return WebUtil.isPostHttpMethod(request);
    }

    public TicketRegistry getTicketRegistry() {
        return ticketRegistry;
    }

    public void setTicketRegistry(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public LogoutManager getLogoutManager() {
        return logoutManager;
    }

    public void setLogoutManager(LogoutManager logoutManager) {
        this.logoutManager = logoutManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
