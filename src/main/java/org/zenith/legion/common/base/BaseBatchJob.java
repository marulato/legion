package org.zenith.legion.common.base;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.sysadmin.entity.BatchJob;
import org.zenith.legion.sysadmin.entity.BatchJobStatus;
import org.zenith.legion.sysadmin.service.BatchJobService;

import java.util.Date;
import java.util.List;

public abstract class BaseBatchJob extends QuartzJobBean {

    private String batchJobId;
    private static final Logger log = LoggerFactory.getLogger(BaseBatchJob.class);

    public BaseBatchJob(String id) {
        this.batchJobId = id;
    }

    public abstract void execute();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (prepare()) {

            execute();
        } else {

        }
    }

    protected boolean prepare() {
        boolean isSuccess = true;
        PlatformTransactionManager transactionManager = SpringUtils.getBean(PlatformTransactionManager.class);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            BatchJobService batchJobService = SpringUtils.getBean(BatchJobService.class);
            BatchJobStatus lastJobStatus = batchJobService.getLastRunningStatus(batchJobId);
            Date now = DateUtils.now();
            BatchJobStatus currentJobStatus = new BatchJobStatus();
            currentJobStatus.setBatchJobId(batchJobId);
            currentJobStatus.setStatus(AppConsts.BATCH_JOB_PROCESSING);
            currentJobStatus.setStartAt(now);
            BatchJob batchJob = batchJobService.getBatchJobById(batchJobId);
            if (lastJobStatus != null && batchJob != null) {
                if (!AppConsts.BATCH_JOB_PROCESSING.equals(lastJobStatus.getStatus()) && DateUtils.getHoursBetween(lastJobStatus.getEndAt(), now) >= 24
                        && DateUtils.isBetween(now, batchJob.getStartAt(), batchJob.getEndAt())) {
                    List<String> dependencyJobs = batchJob.getDependencies();
                    for (String jobId : dependencyJobs) {
                        BatchJobStatus dependencyStatus = batchJobService.getLastRunningStatus(jobId);
                        if (!AppConsts.BATCH_JOB_SUCCESS.equals(dependencyStatus.getStatus()) || !DateUtils.isToday(batchJob.getEndAt())) {
                            isSuccess = false;
                            log.error("Dependent batch job" + batchJobId + " is not running yet");
                            break;
                        }
                    }
                    if (isSuccess) {
                        SQLExecutor.save(currentJobStatus);
                        transactionManager.commit(transactionStatus);
                    }
                } else if (DateUtils.getHoursBetween(lastJobStatus.getEndAt(), now) < 24) {
                    isSuccess = false;
                    log.warn(batchJobId + " Attempt to run was REJECTED : Less than 24 hours since last execution");
                } else if(AppConsts.BATCH_JOB_PROCESSING.equals(lastJobStatus.getStatus())) {
                    isSuccess = false;
                    log.warn(batchJobId + " is running in an another thread");
                }
            }
        } catch (Exception e) {
            isSuccess = false;
            transactionManager.rollback(transactionStatus);
            log.error("", e);
        }
        return isSuccess;
    }

}
