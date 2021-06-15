package com.mapp.sso.server.cache;

import java.util.Collection;
import java.util.Set;

/**
 * cache
 *
 * @author mapp
 */
public interface Cache<K, V> {

    void put(K key, V value);

    V get(K k);

    V remove(K key);

    void clear();

    Set<K> keys();

    Collection<V> values();
}
