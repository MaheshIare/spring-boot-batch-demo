/**
 * 
 */
package com.java.techhub.batch.demo.model;

/**
 * @author mahes
 *
 */
public class StorePayload {
	
	private String name;
	
	private String value;
	
	private PharmacyInfo pharmacyInfo;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the pharmacyInfo
	 */
	public PharmacyInfo getPharmacyInfo() {
		return pharmacyInfo;
	}

	/**
	 * @param pharmacyInfo the pharmacyInfo to set
	 */
	public void setPharmacyInfo(PharmacyInfo pharmacyInfo) {
		this.pharmacyInfo = pharmacyInfo;
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
