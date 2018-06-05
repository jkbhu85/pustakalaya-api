package com.jk.pustakalaya.config;

public final class App {
	private App() {}

	public static final String NAME_ENV_VAR_CONFIG = "PTK_CONFIG";

	public static String getConfigDirPath() {
		return System.getenv(NAME_ENV_VAR_CONFIG);
	}
}
