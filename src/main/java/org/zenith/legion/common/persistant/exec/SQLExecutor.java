package org.zenith.legion.common.persistant.exec;

import org.springframework.stereotype.Service;
import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.utils.SpringUtils;

import java.util.Date;

public class SQLExecutor {

    private static IExecutor executor = SpringUtils.getBean(IExecutor.class);

    public static void save(BasePO entity) {
        if (entity != null) {
            Date now = new Date();
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            executor.insert(entity);
        }
    }

    public static void update(BasePO entity) {
        if (entity != null) {
            Date now = new Date();
            entity.setUpdatedAt(now);
            executor.update(entity);
        }
    }
}
