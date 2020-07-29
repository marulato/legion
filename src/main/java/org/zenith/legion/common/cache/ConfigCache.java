package org.zenith.legion.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ConfigCache implements ICache<String, String> {

    private static final Cache<String, String> cache = CacheBuilder.newBuilder().build();
    public static final String KEY = "org.zenith.legion.common.cache.ConfigCache";

    static {
        ConfigCache configCache = new ConfigCache();
        CachePool.setCache(KEY, configCache);
    }

    @Override
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void set(String key, String value) {
        cache.put(key, value);
    }
}
