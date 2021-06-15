package com.mapp.sso.server.cache;

import cn.hutool.core.util.StrUtil;
import com.mapp.sso.core.exception.SSOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存模板
 *
 * @author mapp
 *
 */
public abstract class AbstractCacheManager<K, V> implements CacheManager<K, V> {

    private Map<String, Cache<K, V>> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Cache<K, V> getCache(String name) {
        if (StrUtil.isBlank(name)) {
            throw new NullPointerException("cache name can not null!");
        }
        Cache<K, V> cache = cacheMap.get(name);
        if (cache == null) {
            cache = createCache(name);
            cacheMap.putIfAbsent(name, cache);
        }

        return cache;
    }

    public abstract Cache<K, V> createCache(String name);
}
