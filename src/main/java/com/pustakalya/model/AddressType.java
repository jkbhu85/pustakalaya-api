package com.pustakalya.model;

/**
 * Represents address types.
 * 
 * @author Jitendra
 *
 */
public enum AddressType {
	PERMANENT("P"),
	CURRENT("C");
	
	private final String code;
	
	private AddressType(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
	
	public AddressType fromCode(String code) {
		if (code != null) {
			code = code.toUpperCase();
			for (AddressType at: AddressType.values()) {
				if (code.equals(at.code)) return at;
			}
		}
		
		return null;
	}
}
