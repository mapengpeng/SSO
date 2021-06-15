package com.mapp.sso.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mapp.sso.core.SSOProperties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * web 工具类
 *
 * @author mapp
 */
public class WebUtil {

    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    public static String getCleanParam(ServletRequest request, String paramName) {
        return StrUtil.trim(request.getParameter(paramName));
    }

    public static void sendRedirect(HttpServletResponse response, String url) throws IOException {
        response.sendRedirect(url);
    }

    public static void redirectToSSOLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(SSOProperties.CAS_SERVICE + "login?service=" + URLEncoder.encode(SSOProperties.CLIENT_SERVICE, "UTF-8"));
    }

    public static void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String service = getCleanParam(request, "service");
        String redirectUrl = request.getContextPath() + "/login";
        if (StrUtil.isNotBlank(service)) {
            redirectUrl = redirectUrl + "?service=" + URLEncoder.encode(service, "UTF-8");
        }
        response.sendRedirect((redirectUrl));
    }

    public static boolean isPostHttpMethod(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod());
    }

    public static boolean isTokenRequest(HttpServletRequest request) {
        return StrUtil.isNotBlank(WebUtil.getCleanParam(request, "ticket"));
    }

    public static boolean isLogoutRequest(HttpServletRequest request) {
        return WebUtil.isPostHttpMethod(request) && StrUtil.isNotBlank(WebUtil.getCleanParam(request, "logoutRequest"));
    }

    public static void writeJson(HttpServletResponse response, int code, String msg) {
        HashMap<String, Object> res = new LinkedHashMap<>();
        res.put("code", code);
        res.put("msg", msg);
        res.put("success", false);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(code);
        try (Writer writer = response.getWriter()) {
            writer.write(JSONUtil.toJsonStr(res));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
