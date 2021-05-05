/**
 * 
 */
package com.java.techhub.batch.demo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.java.techhub.batch.demo.model.RootModel;

/**
 * @author mahes
 *
 */
public class BatchItemProcessor implements ItemProcessor<RootModel, RootModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchItemProcessor.class);
	
	@Autowired
	private DataProcessor dataProcessor;
	
	@Override
	public RootModel process(RootModel processedModel) throws Exception {
		logger.info("Processing root model for flagging...");
		try {
			return dataProcessor.processPatientDetails(processedModel);
		} catch (Exception ex) {
			logger.error("Exception occured while processing the details for flagging due to: {}", ex);
		}
		logger.warn("Something went wrong while flagging the results, hence returning the default model");
		return processedModel;
	}

}
