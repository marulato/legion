package org.zenith.legion.general.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.general.entity.BatchJobStatus;

@Mapper
public interface BatchJobDAO {

    BatchJobStatus getLastRunningStatus(String batchJobId);
}
