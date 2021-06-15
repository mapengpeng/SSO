package com.mapp.sso.server.handler;

import com.mapp.sso.core.authentication.Principal;
import com.mapp.sso.core.util.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证
 *
 * @author mapp
 */
public abstract class AbstractAuthenticationHandler {

    private String username = "username";
    private String password = "password";

    public Principal authenticate(HttpServletRequest request) {
        String username = WebUtil.getCleanParam(request, getUsername());
        String password = WebUtil.getCleanParam(request, getPassword());

        return doAuthenticate(username, password);
    }

    protected abstract Principal doAuthenticate(String username, String password);

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
