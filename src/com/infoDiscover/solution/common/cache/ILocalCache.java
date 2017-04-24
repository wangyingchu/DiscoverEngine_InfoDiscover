package com.infoDiscover.solution.common.cache;

/**
 * Created by sun.
 */
public interface ILocalCache<K, V> {
    public V get(K key);
}
