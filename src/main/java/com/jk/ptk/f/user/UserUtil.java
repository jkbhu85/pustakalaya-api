package com.jk.ptk.f.user;

public final class UserUtil {
	private UserUtil() {
	}

	public static UserProfile toProfile(User u) {
		UserProfile p = new UserProfile();
		
		p.setEmail(u.getEmail());
		p.setFirstName(u.getFirstName());
		p.setLastName(u.getLastName());
		p.setGender(u.getGender());
		p.setImagePath(u.getImagePath());
		p.setIsdCode(u.getIsdCode());
		p.setMobile(u.getMobile());
		p.setBookQuota(u.getBookQuota());
		p.setBookAssignCount(u.getBookAssignCount());
		p.setDateOfBirth(u.getDateOfBirth().toString());
		p.setCreatedOn(u.getCreatedOn().toString());
		p.setLocale(u.getLocaleValue());
		p.setAccountStatus(u.getAccountStatus().getName());
		
		return p;
	}
}
