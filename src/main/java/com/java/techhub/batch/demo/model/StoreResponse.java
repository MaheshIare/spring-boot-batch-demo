/**
 * 
 */
package com.java.techhub.batch.demo.model;

/**
 * @author mahes
 *
 */
public class StoreResponse {
	
	private String code;
	private String message;
	private boolean offlineStatus;
	
	private StorePayload payload;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the offlineStatus
	 */
	public boolean isOfflineStatus() {
		return offlineStatus;
	}

	/**
	 * @param offlineStatus the offlineStatus to set
	 */
	public void setOfflineStatus(boolean offlineStatus) {
		this.offlineStatus = offlineStatus;
	}

	/**
	 * @return the payload
	 */
	public StorePayload getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(StorePayload payload) {
		this.payload = payload;
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
