package org.zenith.legion.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zenith.legion.common.consts.SystemConsts;
import org.zenith.legion.common.utils.LogUtils;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.general.ex.InitializationException;
import org.zenith.legion.sysadmin.dao.ConfigDAO;
import org.zenith.legion.sysadmin.entity.Config;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ConfigCache implements ICache<String, String> {

    private static final LoadingCache<String, String> cache;
    public static final String KEY = "org.zenith.legion.common.cache.ConfigCache";
    private static final Map<String, Properties> propertyList = new HashMap<>();
    private static final List<String> propertyFiles = List.of("dev", "prd", "uat", "legion", "settings");
    private static final Logger log = LoggerFactory.getLogger(ConfigCache.class);

    static {
        cache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                if (propertyList.isEmpty()) {
                    String classPath = SystemConsts.CLASSPATH;
                    File file = new File(classPath);
                    File[] files = file.listFiles();
                    for (File configFile : files) {
                        String baseName = FilenameUtils.getBaseName(configFile.getName());
                        if (configFile.isFile() && "properties".
                                equalsIgnoreCase(FilenameUtils.getExtension(configFile.getName()))
                                && propertyFiles.contains(baseName)) {
                            Properties properties = new Properties();
                            FileInputStream inputStream = new FileInputStream(file);
                            properties.load(inputStream);
                            inputStream.close();
                            propertyList.put(baseName, properties);
                        }
                    }
                    if (propertyList.get("legion") != null) {
                        String mode = propertyList.get("legion").getProperty("server.mode");
                        if (SystemConsts.MODE_DEV.equalsIgnoreCase(mode) && propertyList.containsKey("dev")) {
                            propertyList.remove("prd");
                            propertyList.remove("uat");
                        } else if (SystemConsts.MODE_PRD.equalsIgnoreCase(mode) && propertyList.containsKey("prd")) {
                            propertyList.remove("dev");
                            propertyList.remove("uat");
                        } else if (SystemConsts.MODE_UAT.equalsIgnoreCase(mode) && propertyList.containsKey("uat")) {
                            propertyList.remove("prd");
                            propertyList.remove("dev");
                        } else {
                            throw new InitializationException(LogUtils.print("Initialization FAILED: ", LogUtils.wrapVariable(mode), " NOT found"));
                        }
                    } else {
                        throw new InitializationException("Initialization FAILED: [legion.properties] NOT found");
                    }
                }
                String value = null;
                Set<String> keys = propertyList.keySet();
                for (String name : keys) {
                    Properties currentProp = propertyList.get(name);
                    value = currentProp.getProperty(key);
                    if (value != null) {
                        currentProp.remove(key);
                        if (currentProp.isEmpty()) {
                            propertyList.remove(name);
                            log.info(LogUtils.around("Properties Removed: " + name));
                        }
                        break;
                    }
                }
                if (value == null) {
                    log.info(LogUtils.around("Properties missed, try database"));
                    ConfigDAO configDAO = SpringUtils.getBean(ConfigDAO.class);
                    Config config= configDAO.getConfig(key);
                    if (config != null) {
                        value = config.getConfigValue();
                    }
                }
                return value;
            }
        });
        ConfigCache configCache = new ConfigCache();
        CachePool.setCache(KEY, configCache);
    }

    @Override
    public String get(String key) {
        String value = null;
        try {
            value = cache.get(key);
        } catch (ExecutionException e) {
            log.error("", e);
        }
        return value;
    }

    @Override
    public void set(String key, String value) {
        cache.put(key, value);
    }
}
