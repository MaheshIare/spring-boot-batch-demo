/**
 * 
 */
package com.java.techhub.batch.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author mahes
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class PatientDetailModel {

	private Integer id;
	private List<Patient> patientDetails;
	private String newFlag;
	
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
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the patientDetails
	 */
	public List<Patient> getPatientDetails() {
		return patientDetails;
	}
	/**
	 * @param patientDetails the patientDetails to set
	 */
	public void setPatientDetails(List<Patient> patientDetails) {
		this.patientDetails = patientDetails;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
}
