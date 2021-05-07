/**
 * 
 */
package com.java.techhub.batch.demo.model;

/**
 * @author mahes
 *
 */
public class OperationHours {
	
	private String dayOfWeek;
	private String openTime;
	private String closeTime;
	
	
	/**
	 * @return the dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	/**
	 * @return the openTime
	 */
	public String getOpenTime() {
		return openTime;
	}
	/**
	 * @param openTime the openTime to set
	 */
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	/**
	 * @return the closeTime
	 */
	public String getCloseTime() {
		return closeTime;
	}
	/**
	 * @param closeTime the closeTime to set
	 */
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
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
