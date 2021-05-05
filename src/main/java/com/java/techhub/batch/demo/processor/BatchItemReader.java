/**
 * 
 */
package com.java.techhub.batch.demo.processor;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
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
public class BatchItemReader implements ItemReader<RootModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchItemReader.class);

	private static final String LOCALHOST_URL = "http://localhost:8080/api/data";
	
	@Autowired
	private RestTemplate restTemplate;
	
	private boolean batchJobState = false;

	@Override
	public RootModel read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(!batchJobState) {
			logger.info("Fetching root model from downstream...");
			try {
				RequestEntity<Void> requestEntity = new RequestEntity<Void>(HttpMethod.GET, URI.create(LOCALHOST_URL));
				ResponseEntity<RootModel> responseEntity = restTemplate.exchange(requestEntity, RootModel.class);
				if(responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
					batchJobState = true;
					return responseEntity.getBody();
				}
			} catch (Exception ex) {
				logger.error("Exception occured while fetching the details from downstream due to: {}", ex);
			}
		}
		return null;
	}
	
	/**
	 * @return the batchJobState
	 */
	public boolean isBatchJobState() {
		return batchJobState;
	}

	/**
	 * @param batchJobState the batchJobState to set
	 */
	public void setBatchJobState(boolean batchJobState) {
		this.batchJobState = batchJobState;
	}

}
