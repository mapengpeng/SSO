package com.mapp.sso.server.config;

import com.mapp.sso.server.cache.CacheManager;
import com.mapp.sso.server.cache.DefaultCacheManager;
import com.mapp.sso.server.handler.AbstractAuthenticationHandler;
import com.mapp.sso.server.handler.JdbcTemplateAhthenticationHandler;
import com.mapp.sso.server.interceptor.SSOInterceptor;
import com.mapp.sso.server.interceptor.SSOSessionInterceptor;
import com.mapp.sso.server.manager.LogoutManager;
import com.mapp.sso.server.manager.ServiceManager;
import com.mapp.sso.server.ticket.DefaultMapTicketRegistry;
import com.mapp.sso.server.ticket.TicketRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration
 *
 * 配置类
 *
 * @author mapp
 */
@Configuration
@SuppressWarnings("all")
public class SSOWebConfig implements WebMvcConfigurer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public TicketRegistry ticketRegistry() {
        DefaultMapTicketRegistry registry = new DefaultMapTicketRegistry();
        registry.setCacheManager(cacheManager());
        return registry;
    }

    @Bean
    public CacheManager cacheManager () {
        return new DefaultCacheManager();
    }

    @Bean
    public ServiceManager serviceManager() {
        return new ServiceManager(cacheManager());
    }

    @Bean
    public LogoutManager logoutManager() {
        return new LogoutManager();
    }

    @Bean
    public AbstractAuthenticationHandler authenticationHandler() {
        return new JdbcTemplateAhthenticationHandler(jdbcTemplate);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SSOInterceptor(ticketRegistry(), logoutManager(), serviceManager(), authenticationHandler())).addPathPatterns("/**")
                .excludePathPatterns("/js/**", "/css/**", "/images/**", "/static/**", "/validateServiceTicket");

        registry.addInterceptor(new SSOSessionInterceptor(ticketRegistry())).addPathPatterns("/**")
                .excludePathPatterns("/js/**", "/css/**", "/images/**", "/static/**", "/login", "/doLogin", "/validateServiceTicket");
    }
}
