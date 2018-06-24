package com.jk.ptk.util;

import com.fasterxml.uuid.Generators;

/**
 * Utility class for working with UUIDs.
 * 
 * @author Jitendra
 *
 */
public final class UuidUtils {
	private UuidUtils() {
	}

	/**
	 * Returns string representation of a UUID.
	 * 
	 * @return string representation of a UUID
	 */
	public static String generate() {
		return Generators.randomBasedGenerator().generate().toString();
	}
}
