package com.infoDiscover.solution.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by sun.
 */
public abstract class BaseLoadingCache<K, V> {
    protected final static Logger logger = LoggerFactory.getLogger(BaseLoadingCache.class);

    private int maximumCacheSize = 1000;
    private int expireAfterWriteDuration = 60;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    private LoadingCache<K, V> cache;

    public LoadingCache<K, V> getCache() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    cache = CacheBuilder.newBuilder().maximumSize(maximumCacheSize).recordStats()
                            .expireAfterWrite(expireAfterWriteDuration, timeUnit).build(new CacheLoader<K, V>() {


                                @Override
                                public V load(K k) throws Exception {
                                    return loadData(k);
                                }
                            });
                    logger.debug("Initial local cache successfully!");
                }
            }
        }

        return cache;
    }

    protected abstract V loadData(K k);

    protected V getValue(K key) throws ExecutionException {
        return getCache().getUnchecked(key);
    }

    public int getMaximumCacheSize() {
        return maximumCacheSize;
    }

    public void setMaximumCacheSize(int maximumCacheSize) {
        this.maximumCacheSize = maximumCacheSize;
    }

    public int getExpireAfterWriteDuration() {
        return expireAfterWriteDuration;
    }

    public void setExpireAfterWriteDuration(int expireAfterWriteDuration) {
        this.expireAfterWriteDuration = expireAfterWriteDuration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
