package com.jk.pustakalaya.login;

import java.util.Collection;

public class PtkUserDetails {
	public String id;
	public String name;
	public String email;
	public Collection<String> roles;

	public PtkUserDetails() {
	}

	public PtkUserDetails(String id, String name, String email, Collection<String> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

}
