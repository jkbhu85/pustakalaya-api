package com.pustakalya.model;

/**
 * Represents user roles used in the application.
 * @author Jitendra
 *
 */
public enum UserRole {
	ADMIN("ADMIN"),
	LIBRARIAN("LIBRARIAN"),
	MEMBER("MEMBER"),
	NONE("NONE");

	private final String code;
	
	private UserRole(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
	
	public UserRole fromCode(String code) {
		if (code != null) {
			code = code.toUpperCase();
			
			for (UserRole role: UserRole.values()) {
				if (code.equals(role.code)) {
					return role;
				}
			}
		}
		
		return null;
	}
}
