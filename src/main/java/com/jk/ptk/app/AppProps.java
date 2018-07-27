package com.jk.ptk.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Supplies values of properties to whole application. The system will exit if
 * property loading from files fails.
 * 
 * @author Jitendra
 *
 */
public class AppProps {
	private static final Logger log = LoggerFactory.getLogger(AppProps.class);

	/**
	 * Environment variable name for project configuration directory path.
	 */
	public static final String NAME_ENV_VAR_CONFIG = "PTK_CONFIG";
	
	public static final String CONFIG_DIR_PATH = System.getenv(NAME_ENV_VAR_CONFIG);

	private static final Map<String, String> PROP_MAP = new HashMap<>();

	private static final boolean LOAD_PROPERTIES = true;

	static {
		try {
			init();
		} catch (Exception e) {
			log.error("Could not load properties. Exiting now.{}", e);
			System.exit(-1);
		}
	}

	private static void init() throws Exception {
		if (!LOAD_PROPERTIES)
			return;

		File configDir = new File(CONFIG_DIR_PATH);

		if (!configDir.exists()) {
			throw new FileNotFoundException("The config path is not valid. Path: " + configDir.getAbsolutePath());
		}

		if (!configDir.canRead()) {
			throw new IOException("Can not read the config path. Path: " + configDir.getAbsolutePath());
		}

		if (!configDir.isDirectory()) {
			throw new IOException("Config path is not a directory. Path: {}" + configDir.getAbsolutePath());
		}

		for (File file : configDir.listFiles()) {
			if (!file.getName().endsWith(".properties") || !file.isFile())
				continue;

			if (!file.canRead()) {
				throw new IOException("Can not read the property file. Path: {}" + file.getAbsolutePath());
			}

			// load property file
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			Properties props = new Properties();
			props.load(in);

			// merge property file into property map
			mergeIntoMap(props);

			// close file input stream
			in.close();
		}
	}

	private static void mergeIntoMap(Properties source) {
		for (Object key : source.keySet()) {
			PROP_MAP.put((String) key, (String) source.get(key));
		}
	}

	/**
	 * Returns value associated with {@code key} or {@code null} if {@code key} is
	 * {@code null}.
	 * 
	 * @param key the specified key
	 * @return value associated with {@code key} or {@code null} if {@code key} is
	 *         {@code null}.
	 */
	public static String valueOf(String key) {
		if (key == null || key.isEmpty())
			return null;

		String propValue = PROP_MAP.get(key);

		if (propValue == null)
			onPropertyNotFound(key);

		return propValue;
	}

	private static void onPropertyNotFound(String propName) {
		log.error("Property with name '{}' not found.", propName);
	}
}
