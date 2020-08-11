package org.zenith.legion.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.zenith.legion.common.base.MasterCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MasterCodeCache implements ICache<String, MasterCode>{

    private static final Cache<String, MasterCode> cache = CacheBuilder.newBuilder().build();
    private static final Cache<String, List<MasterCode>> typeCache = CacheBuilder.newBuilder().build();
    public static final String KEY = "org.zenith.legion.common.cache.MasterCodeCache";

    static {
        MasterCodeCache codeCache = new MasterCodeCache();
        CachePool.setCache(KEY, codeCache);
    }

    @Override
    public List<MasterCode> getList(String type) {
        return Collections.unmodifiableList(typeCache.getIfPresent(type) == null ? new ArrayList<>() : typeCache.getIfPresent(type));
    }

    public MasterCode get(String type, String code) {
        return get(type + ":" + code);
    }

    @Override
    public MasterCode get(String keyAndValue) {
        return cache.getIfPresent(keyAndValue);
    }

    @Override
    public void set(String type, MasterCode code) {
        List<MasterCode> masterCodes = typeCache.getIfPresent(type);
        if (masterCodes == null) {
            masterCodes = new ArrayList<>();
            typeCache.put(type, masterCodes);
        }
        masterCodes.add(code);

    }

    public void set(String type, List<MasterCode> codes) {
        List<MasterCode> masterCodes = typeCache.getIfPresent(type);
        if (masterCodes == null) {
            typeCache.put(type, codes);
        } else {
            masterCodes.addAll(codes);
        }

    }

    public void set(String type, String code, MasterCode masterCode) {
        cache.put(type + ":" + code, masterCode);
    }

}
