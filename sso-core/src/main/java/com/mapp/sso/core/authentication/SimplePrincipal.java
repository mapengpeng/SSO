package com.mapp.sso.core.authentication;

import java.util.HashMap;
import java.util.Map;

/**
 *  认证主体
 *
 * @author mapp
 * @date 2021-6-4
 */
public class SimplePrincipal implements Principal {

    private String id;

    private Map<String, Object> attributes;

    public SimplePrincipal() {
        this(null, new HashMap<>());
    }
    public SimplePrincipal(String id) {
        this(id, new HashMap<>());
    }

    public SimplePrincipal(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
