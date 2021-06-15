package com.mapp.sso.server.cache;


import java.util.*;

/**
 * 基于内存实现的缓存
 *
 * @author mapp
 */
public class MapCache<K, V> implements Cache<K, V> {

    private String name;
    private Map<K, V> map;

    public MapCache(String name, Map<K, V> map) {
        this.name = name;
        if (map == null) {
            this.map = new HashMap<>();
        }else {
            this.map = map;
        }
    }

    @Override
    public void put(K key, V value) {
        map.putIfAbsent(key, value);
    }

    @Override
    public V get(K k) {
        return map.get(k);
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keys() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(map.values());
    }
}
