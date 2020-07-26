package org.zenith.legion.common.initializr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.sysadmin.entity.BatchJob;
import org.zenith.legion.sysadmin.service.BatchJobConfigurationService;
import org.zenith.legion.sysadmin.service.BatchJobService;
import java.util.List;

@Component
@Order(1)
public class BatchJobInitializr implements ApplicationRunner {
    @Autowired
    private BatchJobConfigurationService service;
    @Autowired
    private BatchJobService batchJobService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(SpringUtils.getBean(PlatformTransactionManager.class).getClass().getName());
//        List<BatchJob> batchJobs = service.parseXml();
//        for (BatchJob batchJob : batchJobs) {
//            Class.forName(batchJob.getClassName());
//            batchJobService.getBatchJobMap().put(batchJob.getBatchJobId(), batchJob);
//        }
    }
}
