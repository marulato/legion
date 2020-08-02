package org.zenith.legion.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.zenith.legion.common.base.MasterCode;

public class MasterCodeCache implements ICache<String, MasterCode>{

    private static final Cache<String, MasterCode> cache = CacheBuilder.newBuilder().build();
    public static final String KEY = "org.zenith.legion.common.cache.MasterCodeCache";

    static {
        MasterCodeCache codeCache = new MasterCodeCache();
        CachePool.setCache(KEY, codeCache);
    }

    @Override
    public MasterCode get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void set(String key, MasterCode value) {
        cache.put(key, value);

    }
}
