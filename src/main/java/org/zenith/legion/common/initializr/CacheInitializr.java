package org.zenith.legion.common.initializr;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zenith.legion.common.cache.MasterCodeCache;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.sysadmin.dao.MasterCodeDAO;

@Component
public class CacheInitializr implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        MasterCodeCache codeCache = new MasterCodeCache();
        MasterCodeDAO masterCodeDAO = SpringUtils.getBean(MasterCodeDAO.class);
        codeCache.setDepartments(masterCodeDAO.getAllDepartments());
        codeCache.setPositions(masterCodeDAO.getAllPositions());
    }
}
