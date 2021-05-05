/**
 * 
 */
package com.java.techhub.batch.demo.scheduler;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.java.techhub.batch.demo.processor.BatchItemReader;

/**
 * @author mahes
 *
 */
@Component
public class CronJobScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronJobScheduler.class);

	@Autowired
	private Job processDataJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private BatchItemReader batchItemReader;

	@Scheduled(fixedDelay = 60000)
	public void performBatchProcessing() throws Exception {
		logger.info("Job scheduler started at: {}", OffsetDateTime.now());
		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addString("JobID", "Batch-" + UUID.randomUUID().toString()).toJobParameters();
			batchItemReader.setBatchJobState(false);
			JobExecution jobExecution = jobLauncher.run(processDataJob, jobParameters);
			logger.info("Job execution finished with status: {}", jobExecution.getStatus());
		} catch (Exception ex) {
			logger.error("Exception occured in the cronjob scheduler due to: {}", ex);
		}
	}
}
