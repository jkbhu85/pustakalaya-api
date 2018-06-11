package com.jk.pustakalaya.security.auth.jwt;

/**
 * This class represents payload in JWT.
 *
 * @author Jitendra
 *
 */
public class JwtPayload {
	public static final String PAYLOAD_KEY_ID = "id";
	public static final String PAYLOAD_KEY_NAME = "name";
	public static final String PAYLOAD_KEY_EMAIL = "email";
	public static final String PAYLOAD_KEY_ROLE = "role";

	private String id;
	private String name;
	private String email;
	private String role;

	public JwtPayload() {
	}

	/**
	 * Creates an instance with the speicified values.
	 *
	 * @param id
	 *            unique id of the user
	 * @param name
	 *            name of the user
	 * @param email
	 *            email of the user
	 * @param role
	 *            role of the user
	 */
	public JwtPayload(String id, String name, String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param name
	 *            the name to set
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
