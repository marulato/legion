package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.sysadmin.entity.BatchJobStatus;

@Mapper
public interface BatchJobDAO {

    BatchJobStatus getLastRunningStatus(String batchJobId);
}
