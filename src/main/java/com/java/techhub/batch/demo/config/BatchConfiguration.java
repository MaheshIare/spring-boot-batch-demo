/**
 * 
 */
package com.java.techhub.batch.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java.techhub.batch.demo.model.RootModel;
import com.java.techhub.batch.demo.processor.BatchItemProcessor;
import com.java.techhub.batch.demo.processor.BatchItemReader;
import com.java.techhub.batch.demo.processor.BatchItemWriter;
import com.java.techhub.batch.demo.processor.JobCompletionNotificationListener;

/**
 * @author mahes
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<RootModel> reader() {
		return new BatchItemReader();
	}

	@Bean
	public BatchItemProcessor processor() {
		return new BatchItemProcessor();
	}

	@Bean
	public BatchItemWriter writer() {
		return new BatchItemWriter();
	}

	@Bean
	public Job importDataJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importDataJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(BatchItemWriter writer) {
		return stepBuilderFactory.get("step1").<RootModel, RootModel>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}

}
