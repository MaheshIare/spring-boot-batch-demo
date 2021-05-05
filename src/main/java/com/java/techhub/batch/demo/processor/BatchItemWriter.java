/**
 * 
 */
package com.java.techhub.batch.demo.processor;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.java.techhub.batch.demo.model.RootModel;

/**
 * @author mahes
 *
 */
public class BatchItemWriter implements ItemWriter<RootModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchItemWriter.class);
	
	private static final String LOCALHOST_URL = "http://localhost:8080/api/data";
	
	@Autowired
	private RestTemplate restTemplate;
			
	@Override
	public void write(List<? extends RootModel> items) throws Exception {
		logger.info("Flagged patient details");
		for(RootModel model: items) {
			try {
				RequestEntity<RootModel> requestEntity = new RequestEntity<RootModel>(model, HttpMethod.POST, URI.create(LOCALHOST_URL));
				ResponseEntity<RootModel> responseEntity = restTemplate.exchange(requestEntity, RootModel.class);
				if(responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
					logger.info("Successfully sent processed data to downstream API...");
				}
			} catch (Exception ex) {
				logger.error("Exception occured while processing the details after flagging due to: {}", ex);
			}
		}
	}

}
