package com.mapp.sso.server.ticket;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ticket 抽象
 *
 * @author mapp
 */
public abstract class AbstractTicket implements Ticket {

    private String id;
    private Date startTimestamp;
    private Date lastAccessTime;
    private Long timeOut;
    private boolean expired;

    private Map<String, Object> attributes;


    public AbstractTicket() {
    }

    public AbstractTicket(String id, Long timeOut) {
        this.id = id;
        this.timeOut = timeOut;
        this.startTimestamp = new Date();
        this.attributes = new HashMap<>();
    }

    protected void touch() {
        this.lastAccessTime = new Date();
    }

    public void expire() {
        this.expired = true;
    }

    /**
     * 是否超时
     * @return
     */
    public boolean isTimeOut() {
        return System.currentTimeMillis() - getStartTimestamp().getTime() >= getTimeOut();
    }

    /**
     * 校验ticket
     * @return true : 失效  false 不失效
     */
    public boolean validate() {
        if (isExpired()) {
            return true;
        } else {
            return isTimeOut();
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
