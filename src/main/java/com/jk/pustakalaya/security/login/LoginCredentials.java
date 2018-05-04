package com.jk.pustakalaya.security.login;

public class LoginCredentials {
	private String username;
	private String password;
	private String passwordSalt;
	private int passwordVersion;

	public LoginCredentials() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public int getPasswordVersion() {
		return passwordVersion;
	}

	public void setPasswordVersion(int passwordVersion) {
		this.passwordVersion = passwordVersion;
	}

}
