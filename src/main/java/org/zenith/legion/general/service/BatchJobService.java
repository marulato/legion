package org.zenith.legion.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zenith.legion.general.dao.BatchJobDAO;
import org.zenith.legion.general.entity.BatchJob;
import org.zenith.legion.general.entity.BatchJobStatus;

import java.util.HashMap;
import java.util.Map;

@Service
public class BatchJobService {
    @Autowired
    private BatchJobDAO batchJobDAO;
    private Map<String, BatchJob> batchJobMap;

    public BatchJobService() {
        batchJobMap = new HashMap<>();
    }

    public BatchJobStatus getLastRunningStatus(String batchJobId) {
        return batchJobDAO.getLastRunningStatus(batchJobId);
    }

    public Map<String, BatchJob> getBatchJobMap() {
        return batchJobMap;
    }

    public BatchJob getBatchJobById(String batchJobId) {
        return batchJobMap.get(batchJobId);
    }

}
