package com.mapp.sso.core.authentication;

import java.io.Serializable;
import java.util.Map;

/**
 *  认证主体接口
 *
 * @author mapp
 * @date 2021-6-4
 */
public interface Principal extends Serializable {

    Object getId();

    Map<String, Object> getAttributes();
}
