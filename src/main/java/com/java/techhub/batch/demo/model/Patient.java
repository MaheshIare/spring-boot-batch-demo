/**
 * 
 */
package com.java.techhub.batch.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mahes
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class Patient {

	@JsonProperty("patientID")
	private Integer patientId;
	
	private String patientFirstName;
	
	private String patientLastName;
	
	@JsonProperty("patientDOB")
	private String patientDob;
	
	private List<Prescription> prescriptionDetails;
	
	private String isAppointment;
	
	private String newFlag;
	
	public Patient() {
		
	}

	/**
	 * @return the patientId
	 */
	public Integer getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the patientFirstName
	 */
	public String getPatientFirstName() {
		return patientFirstName;
	}

	/**
	 * @param patientFirstName the patientFirstName to set
	 */
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	/**
	 * @return the patientLastName
	 */
	public String getPatientLastName() {
		return patientLastName;
	}

	/**
	 * @param patientLastName the patientLastName to set
	 */
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	/**
	 * @return the patientDob
	 */
	public String getPatientDob() {
		return patientDob;
	}

	/**
	 * @param patientDob the patientDob to set
	 */
	public void setPatientDob(String patientDob) {
		this.patientDob = patientDob;
	}

	/**
	 * @return the prescriptionDetails
	 */
	public List<Prescription> getPrescriptionDetails() {
		return prescriptionDetails;
	}

	/**
	 * @param prescriptionDetails the prescriptionDetails to set
	 */
	public void setPrescriptionDetails(List<Prescription> prescriptionDetails) {
		this.prescriptionDetails = prescriptionDetails;
	}

	/**
	 * @return the isAppointment
	 */
	public String getIsAppointment() {
		return isAppointment;
	}

	/**
	 * @param isAppointment the isAppointment to set
	 */
	public void setIsAppointment(String isAppointment) {
		this.isAppointment = isAppointment;
	}

	/**
	 * @return the newFlag
	 */
	public String getNewFlag() {
		return newFlag;
	}

	/**
	 * @param newFlag the newFlag to set
	 */
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
