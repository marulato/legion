package org.zenith.legion.common.persistant.exec;

import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.utils.SpringUtils;

import java.util.Date;

public class SQLExecutor {

    private static IExecutor executor = SpringUtils.getBean(IExecutor.class);

    public static void save(BasePO entity) {
        if (entity != null) {
            Date now = new Date();
            AppContext appContext = AppContext.getAppContextFromCurrentThread();
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            entity.setCreatedBy(appContext.getLoginId());
            entity.setUpdatedBy(appContext.getLoginId());
            executor.insert(entity);
        }
    }

    public static void update(BasePO entity) {
        if (entity != null) {
            Date now = new Date();
            AppContext appContext = AppContext.getAppContextFromCurrentThread();
            entity.setUpdatedAt(now);
            entity.setUpdatedBy(appContext.getLoginId());
            executor.update(entity);
        }
    }
}
