package com.jk.ptk.security.auth.jwt;

/**
 * This class represents payload in JWT.
 *
 * @author Jitendra
 *
 */
public class JwtPayload {
	private String name;
	private String email;
	private String role;
	private String locale;

	public JwtPayload() {
	}

	/**
	 * Creates an instance with the specified values.
	 *
	 * @param name
	 *            name of the user
	 * @param email
	 *            email of the user
	 * @param role
	 *            role of the user
	 * @param locale
	 *            locale string of the user in LANGUAGE_COUNTRY format.
	 */
	public JwtPayload(String name, String email, String role, String locale) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
		this.locale = locale;
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

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	
}
