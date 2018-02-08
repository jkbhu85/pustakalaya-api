package com.pustakalya.model;

public enum Gender {
	MALE("M"),
	FEMALE("F"),
	OTHER("O");

	private final String code;
	
	private Gender(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
	
	public Gender fromCode(String code) {
		if (code != null) {
			code = code.toUpperCase();
			for (Gender g: Gender.values()) {
				if (code.equals(g.code)) return g;
			}
		}
		
		return null;
	}
}
