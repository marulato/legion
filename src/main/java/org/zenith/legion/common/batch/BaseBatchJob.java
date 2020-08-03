package org.zenith.legion.common.batch;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.general.entity.BatchJob;
import org.zenith.legion.general.entity.BatchJobStatus;
import org.zenith.legion.general.entity.FailedBatchJob;
import org.zenith.legion.general.service.BatchJobService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public abstract class BaseBatchJob extends QuartzJobBean {

    private String batchJobId;
    private static final Logger log = LoggerFactory.getLogger(BaseBatchJob.class);

    public BaseBatchJob(String id) {
        this.batchJobId = id;
    }

    public abstract void execute() throws Exception;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean isSuccess = false;
        Exception ex = null;
        if (prepare(null)) {
            try {
                execute();
                isSuccess = true;
            } catch (Exception e) {
                ex = e;
                log.error("Batch job: " + batchJobId + " encountered an exception", e);
            } finally {
                conclude(isSuccess, ex);
            }

        }
    }

    protected void executeManual(HttpServletRequest request) {
        boolean isSuccess = false;
        Exception ex = null;
        if (prepare(request)) {
            try {
                execute();
                isSuccess = true;
            } catch (Exception e) {
                ex = e;
                log.error("Batch job: " + batchJobId + " encountered an exception", e);
            } finally {
                conclude(isSuccess, ex);
            }

        }
    }

    protected boolean prepare(HttpServletRequest request) {
        boolean isSuccess = true;
        AppContext context = null;
        BatchJobStatus currentJobStatus = new BatchJobStatus();
        try {
            if (request == null) {
                context = AppContext.getAppContextFromCurrentThread(true);
                context.setLoginId(batchJobId);
                context.setDomain("SYSTEM");
                AppContext.setLocalThreadAppContext(context);
                currentJobStatus.setTriggeredBy("Internal Job Scheduler");
            } else {
                currentJobStatus.setTriggeredBy(AppContext.getAppContextFromCurrentThread().getDomain() + " User");
            }
            BatchJobService batchJobService = SpringUtils.getBean(BatchJobService.class);
            BatchJobStatus lastJobStatus = batchJobService.getLastRunningStatus(batchJobId);
            Date now = DateUtils.now();
            currentJobStatus.setBatchJobId(batchJobId);
            currentJobStatus.setStatus(AppConsts.BATCH_JOB_PROCESSING);
            currentJobStatus.setStartAt(now);
            BatchJob batchJob = batchJobService.getBatchJobById(batchJobId);
            if (lastJobStatus != null && batchJob != null) {
                if (!AppConsts.BATCH_JOB_PROCESSING.equals(lastJobStatus.getStatus())
                        && DateUtils.isBetween(now, batchJob.getStartAt(), batchJob.getEndAt())) {
                    if (DateUtils.getHoursBetween(lastJobStatus.getEndAt(), now) >= 24 || request != null) {
                        List<String> dependencyJobs = batchJob.getDependencies();
                        for (String jobId : dependencyJobs) {
                            BatchJobStatus dependencyStatus = batchJobService.getLastRunningStatus(jobId);
                            if (!AppConsts.BATCH_JOB_SUCCESS.equals(dependencyStatus.getStatus()) || !DateUtils.isToday(batchJob.getEndAt())) {
                                isSuccess = false;
                                log.error("Dependent batch job " + batchJobId + " is not running yet");
                                break;
                            }
                        }
                    } else {
                        isSuccess = false;
                        log.warn(batchJobId + " Attempt to run was REJECTED : Less than 24 hours since last execution");
                    }
                    if (isSuccess) {
                        SQLExecutor.save(currentJobStatus);
                        log.info("Batch job: " + batchJobId +"is ready to tun");
                    }
                } else if(AppConsts.BATCH_JOB_PROCESSING.equals(lastJobStatus.getStatus())) {
                    isSuccess = false;
                    log.warn(batchJobId + " is running in an another thread");
                } else {

                }isSuccess = false;
                log.warn(batchJobId + " is EXPIRED");
            }
        } catch (Exception e) {
            isSuccess = false;
            log.error("", e);
        }
        return isSuccess;
    }

    protected void conclude(boolean isSuccess, Exception e) {
        BatchJobService batchJobService = SpringUtils.getBean(BatchJobService.class);
        BatchJobStatus lastJobStatus = batchJobService.getLastRunningStatus(batchJobId);
        if (isSuccess) {
            lastJobStatus.setEndAt(DateUtils.now());
            lastJobStatus.setStatus(AppConsts.BATCH_JOB_SUCCESS);
            SQLExecutor.update(lastJobStatus);
        } else if (e != null) {
            lastJobStatus.setEndAt(DateUtils.now());
            lastJobStatus.setStatus(AppConsts.BATCH_JOB_FAILED);
            FailedBatchJob failedBatchJob = new FailedBatchJob();
            failedBatchJob.setBatchJobId(batchJobId);
            failedBatchJob.setStartAt(lastJobStatus.getStartAt());
            failedBatchJob.setEndAt(lastJobStatus.getEndAt());
            failedBatchJob.setException(ExceptionUtils.getStackTrace(e).getBytes(StandardCharsets.UTF_8));
            SQLExecutor.update(lastJobStatus);
            SQLExecutor.save(failedBatchJob);
        }
    }

}
