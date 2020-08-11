package org.zenith.legion.common.utils;

import org.zenith.legion.common.base.MasterCode;
import org.zenith.legion.common.cache.CachePool;
import org.zenith.legion.common.cache.ICache;
import org.zenith.legion.common.cache.MasterCodeCache;
import org.zenith.legion.sysadmin.dao.ConfigDAO;

import java.util.List;

public class MasterCodeUtils {

    private static MasterCodeCache cache = CachePool.getCache(MasterCodeCache.KEY, MasterCodeCache.class);
    private static ConfigDAO configDAO = SpringUtils.getBean(ConfigDAO.class);

    public static MasterCode getMasterCode(String type, String value) {
        MasterCode masterCode = cache.get(type, value);
        if (masterCode == null) {
            configDAO.getMasterCode(type, value);
        }
        return masterCode;
    }

    public static List<MasterCode> getMasterCode(String type) {
        List<MasterCode> masterCode = cache.getList(type);
        if (masterCode == null || masterCode.isEmpty()) {
            masterCode = configDAO.getMasterCodeByType(type);
        }
        return masterCode;
    }
}
