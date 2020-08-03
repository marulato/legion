package org.zenith.legion.common.initializr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.zenith.legion.common.cache.BatchJobCache;
import org.zenith.legion.common.cache.CachePool;
import org.zenith.legion.general.entity.BatchJob;
import org.zenith.legion.general.ex.InvalidBatchJobException;
import org.zenith.legion.general.service.BatchJobConfigurationService;
import org.zenith.legion.general.service.BatchJobService;
import java.util.List;

@Order(1)
public class BatchJobInitializr implements ApplicationRunner {
    @Autowired
    private BatchJobConfigurationService service;
    @Autowired
    private BatchJobService batchJobService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<BatchJob> definedBatchJobs = service.parseXml();
        for (BatchJob batchJob : definedBatchJobs) {
            StringBuilder errorMsg =service.validateBatchJob(batchJob, definedBatchJobs);
            BatchJobCache batchJobCache = CachePool.getCache(BatchJobCache.KEY, BatchJobCache.class);
            if (errorMsg != null && errorMsg.length() > 0) {
                batchJobCache.cleanUp();
                throw new InvalidBatchJobException(errorMsg.toString());
            }
            batchJobCache.set(batchJob.getBatchJobId(), batchJob);
        }
    }
}
