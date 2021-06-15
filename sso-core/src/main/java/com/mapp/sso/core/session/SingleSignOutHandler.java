package com.mapp.sso.core.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sso 登录，退出拦截器
 *
 * @author mapp
 */
public final class SingleSignOutHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SingleSignOutHandler.class);

    // ticket-session map
    private Map<String , HttpSession> sessionMap = new ConcurrentHashMap<>();

    public void addSession(String ticket, HttpSession session) {
        sessionMap.put(ticket, session);
    }

    public void removeSession(String ticket) {
        HttpSession httpSession = sessionMap.get(ticket);
        if (httpSession != null) {
            try {
                httpSession.invalidate();
            }catch (Exception e) {
                LOG.info("session invalidate error , ticket:{}", ticket);
            }
        }
    }
}
