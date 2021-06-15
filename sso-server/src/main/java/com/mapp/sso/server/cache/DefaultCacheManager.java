package com.mapp.sso.server.cache;

import java.util.HashMap;

/**
 * 基于内存实现
 * @param <K>
 * @param <V>
 */
public class DefaultCacheManager<K, V> extends AbstractCacheManager<K, V> {

    @Override
   public Cache<K, V> createCache(String name) {
        return new MapCache<>(name, new HashMap<>());
    }
}
