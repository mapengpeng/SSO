package com.mapp.sso.core.filter;

import cn.hutool.core.util.StrUtil;
import com.mapp.sso.core.SSOProperties;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * 拦截器父类，提供了Ant路径匹配规则
 *
 * @author mapp
 */
public abstract class AbstractSSOFilter implements Filter {

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private String[] ignoreUrlArr;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ignoreUrl = SSOProperties.IGNORE_URL;
        if (StrUtil.isNotBlank(ignoreUrl)) {
            ignoreUrlArr = ignoreUrl.split(",");
        }
    }

    /**
     * 需要放行的资源路径
     * @param servletPath
     * @return
     */
    protected boolean isIgnore(String servletPath) {
        boolean flag = false;
        if (ignoreUrlArr != null) {
            for (String s : ignoreUrlArr) {
                if (pathMatcher.match(s, servletPath)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
