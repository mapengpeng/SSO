package com.mapp.sso.server.ticket;


import com.mapp.sso.core.exception.SSOException;

/**
 * ST
 *
 * 票据
 *
 * @author mapp
 *
 */
public class ServiceTicket extends AbstractTicket {

    // 客户端访问地址
    private String serviceUrl;

    private TgtTicket tgtTicket;

    public ServiceTicket() {
    }

    public ServiceTicket(String id, Long timeOut) {
        super(id, timeOut);
    }

    public ServiceTicket(String id, Long timeOut, String serviceUrl) {
        super(id, timeOut);
        this.serviceUrl = serviceUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    protected void touch() {
        throw new SSOException("ServiceTicket can not touch!");
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public boolean isTimeOut() {
        return true;
    }

    public boolean validateService(String service) {
        return serviceUrl.equals(service);
    }

    public TgtTicket getTgtTicket() {
        return tgtTicket;
    }

    public void setTgtTicket(TgtTicket tgtTicket) {
        this.tgtTicket = tgtTicket;
    }
}
