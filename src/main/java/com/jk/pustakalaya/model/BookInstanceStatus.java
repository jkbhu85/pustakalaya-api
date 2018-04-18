package com.jk.pustakalaya.model;

public enum BookInstanceStatus {
	ISSUED("ISSUED"),
	AVAILABLE("AVAILABLE"),
	REMOVED("REMOVED");
	
	private final String code;
	
	private BookInstanceStatus(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
	
	public BookInstanceStatus fromCode(String code) {
		if (code != null) {
			code = code.toUpperCase();
			for (BookInstanceStatus bis: BookInstanceStatus.values()) {
				if (code.equals(bis.code)) return bis;
			}
		}
		
		return null;
	}
}