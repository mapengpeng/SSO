package com.mapp.sso.server.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import com.mapp.sso.server.service.WebApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 单点退出
 *
 * @author mapp
 */
public class LogoutManager {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutManager.class);

    @Autowired
    private ServiceManager serviceManager;

    public void logout(String ticket) {
        LOG.info("单点退出， ticket:{}", ticket);
        Set<WebApplicationService> service = serviceManager.getService(ticket);
        if (CollUtil.isNotEmpty(service)) {
            for (WebApplicationService webApplicationService : service) {
                try {
                    Map<String, Object> parms = new HashMap<>();
                    parms.put("logoutRequest", webApplicationService.getTicket());
                    // 发送http请求，退出子系统
                    HttpUtil.post(webApplicationService.getUrl(), parms);
                }catch (Exception e) {
                    LOG.error("单点退出失败！service:{}", webApplicationService.toString());
                }
            }
            serviceManager.clearService(ticket);
        }
    }
}
