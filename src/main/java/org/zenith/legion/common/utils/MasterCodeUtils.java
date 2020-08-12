package org.zenith.legion.common.utils;

import org.zenith.legion.sysadmin.entity.MasterCode;
import org.zenith.legion.common.cache.CachePool;
import org.zenith.legion.common.cache.MasterCodeCache;
import org.zenith.legion.sysadmin.dao.MasterCodeDAO;

import java.util.List;

public class MasterCodeUtils {

    private static MasterCodeCache cache = CachePool.getCache(MasterCodeCache.KEY, MasterCodeCache.class);
    private static MasterCodeDAO configDAO = SpringUtils.getBean(MasterCodeDAO.class);

    public static MasterCode getMasterCode(String type, String value) {
        MasterCode masterCode = cache.get(type, value);
        if (masterCode == null) {
            configDAO.getMasterCode(type, value);
        }
        return masterCode;
    }

}
