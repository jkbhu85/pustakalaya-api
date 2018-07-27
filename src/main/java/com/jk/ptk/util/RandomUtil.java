package com.jk.ptk.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Utility class that facilitates to work with cryptographically secure random numbers.
 *
 * @author Jitendra
 *
 */
public final class RandomUtil {
	private static final SecureRandom GENERATOR = new SecureRandom();
	private static final int POSITIVE_MASK = 0x7f;

	private RandomUtil() {
	}

	/**
	 * Returns a string of length {@code len} which is a hexadecimal representation
	 * of a cryptographically secure random non-negative integer.
	 *
	 * @param len
	 *            number of digits in the hexadecimal number
	 * @return a string of length {@code len} which is a hexadecimal representation
	 *         of a cryptographically secure random non-negative integer
	 */
	public static String getNoramlizedRandomHex(int len) {
		if ((len & 1) == 1) {
			len += 1;
			return toNoramlizedHexString(getRandomBytes(len / 2)).substring(1);
		} else {
			return toNoramlizedHexString(getRandomBytes(len / 2));
		}
	}

	/**
	 * Returns an array of random bytes which are cryptographically secure.
	 *
	 * @param len
	 *            number of bytes
	 * @return an array of random bytes which are cryptographically secured
	 */
	public static byte[] getRandomBytes(int len) {
		byte[] randomBytes = new byte[len];
		GENERATOR.nextBytes(randomBytes);

		return randomBytes;
	}

	/**
	 * Always returns a hexadecimal string which is equivalent to a non-zero
	 * integer. <b>Do not</b> use this method if you want exact representation of
	 * {@code src} as string.
	 *
	 * @param src
	 *            source byte array
	 * @return a hexadecimal string which is equivalent to a non-zero integer
	 */
	public static String toNoramlizedHexString(byte[] src) {
		src[0] = (byte) (src[0] & POSITIVE_MASK);

		if (src[0] < 16) {
			src[0] = 16;
		}

		BigInteger bi = new BigInteger(src);
		String s = bi.toString(16);

		return s;
	}

	/**
	 * Returns a hexadecimal string which is an exact representation of the
	 * {@code src}.
	 *
	 * @param src
	 *            source byte array
	 * @return a hexadecimal string which is exact representation of the bytes
	 */
	public static String toHexString(byte[] src) {
		BigInteger bi = new BigInteger(src);
		String s = bi.toString(16);

		return s;
	}

}
