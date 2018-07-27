package com.jk.ptk.util;

import com.jk.ptk.security.auth.jwt.JwtPayload;

public class UserUtil {
	private static final ThreadLocal<JwtPayload> repo = new ThreadLocal<>();
	
	public static void setPayload(JwtPayload payload) {
		repo.set(payload);
	}
	
	public static void removePayload() {
		repo.remove();
	}
	
	
	public static String getEmail() {
		if (repo.get() != null) return repo.get().getEmail();
		
		return null;
	}
	
	public static String getLocaleValue() {
		if (repo.get() != null) return repo.get().getLocale();
		
		return null;
	}
	
	public static String getRoleValue() {
		if (repo.get() != null) return repo.get().getRole();
		
		return null;
	}
}
