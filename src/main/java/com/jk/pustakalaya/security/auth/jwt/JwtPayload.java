package com.jk.pustakalaya.security.auth.jwt;

public class JwtPayload {
	public static final String PAYLOAD_KEY_ID = "id";
	public static final String PAYLOAD_KEY_NAME = "name";
	public static final String PAYLOAD_KEY_EMAIL = "email";
	public static final String PAYLOAD_KEY_ROLES = "roles";

	private String id;
	private String name;
	private String email;
	private String[] roles;

	public JwtPayload() {}

	/**
	 * @param id
	 * @param name
	 * @param email
	 * @param roles
	 */
	public JwtPayload(String id, String name, String email, String[] roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the roles
	 */
	public String[] getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String[] roles) {
		this.roles = roles;
	}



}
