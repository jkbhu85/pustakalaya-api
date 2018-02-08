package com.pustakalya.model;

/**
 * Represents country in database.
 * 
 * @author Jitendra
 *
 */
public class Country {
	private int countryId;
	private String name;
	private String isdCode;
	private String abbr;
	
	public Country() {
		this(0);
	}
	
	public Country(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
	
}
