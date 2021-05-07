/**
 * 
 */
package com.java.techhub.batch.demo.model;

import java.util.List;

/**
 * @author mahes
 *
 */
public class PharmacyInfo {
	
	private List<OperationHours> hoursOfOperation;

	/**
	 * @return the hoursOfOperation
	 */
	public List<OperationHours> getHoursOfOperation() {
		return hoursOfOperation;
	}

	/**
	 * @param hoursOfOperation the hoursOfOperation to set
	 */
	public void setHoursOfOperation(List<OperationHours> hoursOfOperation) {
		this.hoursOfOperation = hoursOfOperation;
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
