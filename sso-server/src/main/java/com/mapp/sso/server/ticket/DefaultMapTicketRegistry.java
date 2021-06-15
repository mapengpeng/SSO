package com.mapp.sso.server.ticket;

import com.mapp.sso.server.cache.DefaultCacheManager;


/**
 * 票根接口默认实现
 *
 * @author mapp
 * @date 2021-6-4
 */
public class DefaultMapTicketRegistry extends AbstractCacheTicketRegistry {

    public DefaultMapTicketRegistry() {
        setCacheManager(new DefaultCacheManager<>());
    }
}
