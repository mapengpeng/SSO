package com.mapp.sso.server.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 子系统信息实体
 *
 * @author mapp
 */
public class WebApplicationService implements Serializable {

    private String id;
    private String url;
    private String code;
    private String name;
    private String ticket;
    private Map<String, String> attrs;

    public WebApplicationService() {
        this.attrs = new HashMap<>();
    }

    public WebApplicationService(String id, String url, String ticket) {
        this();
        this.id = id;
        this.url = url;
        this.ticket = ticket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebApplicationService that = (WebApplicationService) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(url, that.url) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ticket, that.ticket) &&
                Objects.equals(attrs, that.attrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, code, name, ticket, attrs);
    }

    @Override
    public String toString() {
        return "WebApplicationService{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", ticket='" + ticket + '\'' +
                ", attrs=" + attrs +
                '}';
    }
}
