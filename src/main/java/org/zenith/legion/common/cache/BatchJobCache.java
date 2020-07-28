package org.zenith.legion.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.zenith.legion.sysadmin.entity.BatchJob;

public class BatchJobCache implements ICache<String, BatchJob>{


    private static final Cache<String, BatchJob> cache = CacheBuilder.newBuilder().build();
    public static final String KEY = "org.zenith.legion.common.cache.BatchJobCache";

    static {
        BatchJobCache batchJobCache = new BatchJobCache();
        CachePool.setCache(KEY, batchJobCache);
    }

    @Override
    public BatchJob get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void set(String key, BatchJob value) {
        cache.put(key, value);
    }

    public void remove(String key) {
        cache.invalidate(key);
    }

    public void cleanUp() {
        cache.invalidateAll();
    }
}
