package com.mapp.sso.client.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * sso session拦截器
 *
 * @author mapp
 */
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("userInfo") != null) {
//            return true;
//        }else {
//            WebUtil.sendRedirect(response, SSOProperties.CAS_SERVICE + "/login?service=" + SSOProperties.CLIENT_SERVICE);
//            return false;
//        }
        return true;
    }
}
