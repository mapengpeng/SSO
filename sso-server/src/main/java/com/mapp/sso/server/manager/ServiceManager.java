package com.mapp.sso.server.manager;

import com.mapp.sso.server.cache.Cache;
import com.mapp.sso.server.cache.CacheManager;
import com.mapp.sso.server.cache.DefaultCacheManager;
import com.mapp.sso.server.service.WebApplicationService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 子系统管理器
 *
 * @author mapp
 */
public class ServiceManager {

    private String cacheName = "serviceCache";

    private CacheManager<String, Set<WebApplicationService>> cacheManager;

    private Cache<String, Set<WebApplicationService>> cache;

    public ServiceManager() {

    }

    public ServiceManager(CacheManager<String, Set<WebApplicationService>> cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void addService(String ticketId, WebApplicationService service) {
        Cache<String, Set<WebApplicationService>> cache = getCache();
        Set<WebApplicationService> list = cache.get(ticketId);
        if (list == null) {
            list = new LinkedHashSet<>();
        }
        list.add(service);
        cache.put(ticketId, list);
    }

    public Set<WebApplicationService> getService(String ticket) {
        return getCache().get(ticket);
    }

    public void clearService(String ticket) {
        getCache().get(ticket).clear();
    }

    public void clear() {
        getCache().clear();
    }

    public CacheManager<String, Set<WebApplicationService>> getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager<String, Set<WebApplicationService>> cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Cache<String, Set<WebApplicationService>> getCache() {
        if (cache == null) {
            cache = getCacheManager().getCache(cacheName);
        }
        return cache;
    }

    public void setCache(Cache<String, Set<WebApplicationService>> cache) {
        this.cache = cache;
    }
}
