package org.zenith.legion.common.utils;

import org.zenith.legion.common.cache.CachePool;
import org.zenith.legion.common.cache.ConfigCache;

public class ConfigUtils {

    private static ConfigCache configCache = CachePool.getCache(ConfigCache.KEY, ConfigCache.class);

    public static String get(String key) {
        return configCache.get(key);
    }
}
