package com.mapp.sso.server.handler;

/**
 * 接入子系统校验，用于校验子系统是否可以允许接入sso服务
 *
 * @author mapp
 */
public class ServiceCheckHandler {


    public boolean check(String service) {
        // TODO 可自定义实现，默认不校验
        return true;
    }
}
