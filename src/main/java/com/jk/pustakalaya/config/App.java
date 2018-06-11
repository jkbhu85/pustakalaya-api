package com.jk.pustakalaya.config;

public final class App {
	private App() {
	}

	private static final String APP_DOMAIN = "http://localhost:8080";
	private static final int REGISTRATION_LINK_EXPIRATION_HOURS = 72;

	/**
	 * Environment variable name for project configuration directory path.
	 */
	public static final String NAME_ENV_VAR_CONFIG = "PTK_CONFIG";

	/**
	 * Returns path of the project configuration directory.
	 * 
	 * @return path of the project configuration directory
	 */
	public static String configDirPath() {
		return System.getenv(NAME_ENV_VAR_CONFIG);
	}

	/**
	 * Returns URL for the specified URI.
	 * 
	 * @param uri
	 *            the speicified uri.
	 * @return URL for the specified URI
	 */
	public static String getUrl(String uri) {
		return appDomain() + "/" + uri;
	}

	/**
	 * Returns registration link expiration duration in hours.
	 * 
	 * @return registration link expiration duration in hours
	 */
	public static int registrationLinkExpireDuration() {
		return REGISTRATION_LINK_EXPIRATION_HOURS;
	}

	/**
	 * Returns internet domain of the application.
	 * 
	 * @return internet domain of the application
	 */
	public static String appDomain() {
		return APP_DOMAIN;
	}
}
