/**
 * 
 */
package com.java.techhub.batch.demo.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author mahes
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class Prescription {

	private String rxNumber;

	private String drugName;

	private String drugQuantity;

	private String imzType;

	private String checkInTime;

	private String preferredtextInd;

	private String digitalQuestionaireReceivedInd;

	private String imzDoseNumber;

	private String imzScriptVerifiedInd;

	private String cardImageCaptureRequired;

	private String isRelationshipToPatientRequired;

	private String newFlag;

	public Prescription() {
		// Default constructor
	}

	/**
	 * @return the rxNumber
	 */
	public String getRxNumber() {
		return rxNumber;
	}

	/**
	 * @param rxNumber the rxNumber to set
	 */
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}

	/**
	 * @return the drugName
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 * @param drugName the drugName to set
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	/**
	 * @return the drugQuantity
	 */
	public String getDrugQuantity() {
		return drugQuantity;
	}

	/**
	 * @param drugQuantity the drugQuantity to set
	 */
	public void setDrugQuantity(String drugQuantity) {
		this.drugQuantity = drugQuantity;
	}

	/**
	 * @return the imzType
	 */
	public String getImzType() {
		return imzType;
	}

	/**
	 * @param imzType the imzType to set
	 */
	public void setImzType(String imzType) {
		this.imzType = imzType;
	}

	/**
	 * @return the checkInTime
	 */
	public String getCheckInTime() {
		return checkInTime;
	}

	/**
	 * @param checkInTime the checkInTime to set
	 */
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	/**
	 * @return the preferredtextInd
	 */
	public String getPreferredtextInd() {
		return preferredtextInd;
	}

	/**
	 * @param preferredtextInd the preferredtextInd to set
	 */
	public void setPreferredtextInd(String preferredtextInd) {
		this.preferredtextInd = preferredtextInd;
	}

	/**
	 * @return the digitalQuestionaireReceivedInd
	 */
	public String getDigitalQuestionaireReceivedInd() {
		return digitalQuestionaireReceivedInd;
	}

	/**
	 * @param digitalQuestionaireReceivedInd the digitalQuestionaireReceivedInd to
	 *                                       set
	 */
	public void setDigitalQuestionaireReceivedInd(String digitalQuestionaireReceivedInd) {
		this.digitalQuestionaireReceivedInd = digitalQuestionaireReceivedInd;
	}

	/**
	 * @return the imzDoseNumber
	 */
	public String getImzDoseNumber() {
		return imzDoseNumber;
	}

	/**
	 * @param imzDoseNumber the imzDoseNumber to set
	 */
	public void setImzDoseNumber(String imzDoseNumber) {
		this.imzDoseNumber = imzDoseNumber;
	}

	/**
	 * @return the imzScriptVerifiedInd
	 */
	public String getImzScriptVerifiedInd() {
		return imzScriptVerifiedInd;
	}

	/**
	 * @param imzScriptVerifiedInd the imzScriptVerifiedInd to set
	 */
	public void setImzScriptVerifiedInd(String imzScriptVerifiedInd) {
		this.imzScriptVerifiedInd = imzScriptVerifiedInd;
	}

	/**
	 * @return the cardImageCaptureRequired
	 */
	public String getCardImageCaptureRequired() {
		return cardImageCaptureRequired;
	}

	/**
	 * @param cardImageCaptureRequired the cardImageCaptureRequired to set
	 */
	public void setCardImageCaptureRequired(String cardImageCaptureRequired) {
		this.cardImageCaptureRequired = cardImageCaptureRequired;
	}

	/**
	 * @return the isRelationshipToPatientRequired
	 */
	public String getIsRelationshipToPatientRequired() {
		return isRelationshipToPatientRequired;
	}

	/**
	 * @param isRelationshipToPatientRequired the isRelationshipToPatientRequired to
	 *                                        set
	 */
	public void setIsRelationshipToPatientRequired(String isRelationshipToPatientRequired) {
		this.isRelationshipToPatientRequired = isRelationshipToPatientRequired;
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

	@JsonIgnore
	public boolean isPrescriptionFlagged() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX");
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime prescriptionDateTime = LocalDateTime.parse(getCheckInTime(), formatter);
		Duration duration = Duration.between(prescriptionDateTime, currentDateTime);
		if (prescriptionDateTime.compareTo(currentDateTime) < 0) {
			long diff = duration.toMinutes();
			if (Math.abs(diff) <= 60) {
				setNewFlag("Flagged");
				return true;
			}
		}
		return false;
	}

}
