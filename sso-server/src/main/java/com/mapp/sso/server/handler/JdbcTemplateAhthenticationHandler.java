package com.mapp.sso.server.handler;

import com.mapp.sso.core.authentication.Principal;
import com.mapp.sso.core.authentication.SimplePrincipal;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 基于数据库查询的处理类
 *
 * @author mapp
 */
public class JdbcTemplateAhthenticationHandler extends AbstractAuthenticationHandler {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateAhthenticationHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected Principal doAuthenticate(String username, String password) {
        // TODO 数据库查询验证
        SimplePrincipal simplePrincipal = new SimplePrincipal();
        simplePrincipal.setId("admin");
        return simplePrincipal;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
