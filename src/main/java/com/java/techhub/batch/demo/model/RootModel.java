/**
 * 
 */
package com.java.techhub.batch.demo.model;

/**
 * @author mahes
 *
 */
public class RootModel {

	private PatientDetailModel patientDetailModel;
	
	private StoreResponse storeResponse;

	/**
	 * @return the patientDetailModel
	 */
	public PatientDetailModel getPatientDetailModel() {
		return patientDetailModel;
	}

	/**
	 * @param patientDetailModel the patientDetailModel to set
	 */
	public void setPatientDetailModel(PatientDetailModel patientDetailModel) {
		this.patientDetailModel = patientDetailModel;
	}

	/**
	 * @return the storeResponse
	 */
	public StoreResponse getStoreResponse() {
		return storeResponse;
	}

	/**
	 * @param storeResponse the storeResponse to set
	 */
	public void setStoreResponse(StoreResponse storeResponse) {
		this.storeResponse = storeResponse;
	}
	
	
}
