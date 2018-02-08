package com.pustakalya.model;

public enum UserAcStatus {
	ACTIVE("ACTIVE"),
	CLOSED("CLOSED"),
	REVOKED("REVOKED"),
	LOCKED("LOCKED"),
	INCOMPLETE("INCOMPLETE");
	
	private final String code;
	
	private UserAcStatus(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
	
	public UserAcStatus fromCode(String code) {
		if (code != null) {
			code = code.toUpperCase();
			for (UserAcStatus uac: UserAcStatus.values()) {
				if (code.equals(uac.code)) return uac;
			}
		}
		
		return null;
	}
}
