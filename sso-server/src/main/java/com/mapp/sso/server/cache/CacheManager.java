package com.mapp.sso.server.cache;

/**
 * 缓存管理器
 *
 * @author mapp
 *
 */
public interface CacheManager<K, V> {

    Cache<K, V> getCache(String name);
}
