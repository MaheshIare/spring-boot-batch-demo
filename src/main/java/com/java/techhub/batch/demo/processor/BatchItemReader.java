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

import com.java.techhub.batch.demo.model.PatientDetailModel;
import com.java.techhub.batch.demo.model.RootModel;
import com.java.techhub.batch.demo.model.StoreResponse;

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
		if (!batchJobState) {
			logger.info("Fetching root model from downstream...");
			PatientDetailModel patientDetailModel = null;
			StoreResponse storeResponse = null;
			RootModel rootModel = null;
			try {
				rootModel = new RootModel();
				// API call to fetch patient details
				RequestEntity<Void> requestEntity = new RequestEntity<Void>(HttpMethod.GET,
						URI.create(LOCALHOST_URL + "/patient"));
				ResponseEntity<PatientDetailModel> responseEntity = restTemplate.exchange(requestEntity,
						PatientDetailModel.class);
				if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
					patientDetailModel = responseEntity.getBody();
				}

				// API call to fetch store details
				RequestEntity<Void> requestStoreEntity = new RequestEntity<Void>(HttpMethod.GET,
						URI.create(LOCALHOST_URL + "/store"));
				ResponseEntity<StoreResponse> responseStoreEntity = restTemplate.exchange(requestStoreEntity,
						StoreResponse.class);
				if (responseStoreEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
					storeResponse = responseStoreEntity.getBody();
				}
				rootModel.setPatientDetailModel(patientDetailModel);
				rootModel.setStoreResponse(storeResponse);
				batchJobState = true;
				return rootModel;
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
