package com.jk.pustakalaya.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class RandomUtil {
	private static final SecureRandom GENERATOR = new SecureRandom();
	private static final int POSITIVE_MASK = 0x7f;

	private RandomUtil() {
	}

	public static String getRandomHex(int len) {
		return toHexString(getRandom(len));
	}

	public static byte[] getRandom(int len) {
		byte[] randomBytes = new byte[len];
		GENERATOR.nextBytes(randomBytes);

		return randomBytes;
	}

	/**
	 * Always returns a hexadecimal string which is equivalent to a non-zero
	 * integer. <b>Do not</b> use this method if you want exact representation of
	 * bytes as string.
	 *
	 * @param src
	 *            source byte array
	 * @return a hexadecimal string which is equivalent to a non-zero integer
	 */
	public static String toHexString(byte[] src) {
		src[0] = (byte) (src[0] & POSITIVE_MASK);

		if (src[0] < 16) {
			src[0] = 16;
		}

		BigInteger bi = new BigInteger(src);
		String s = bi.toString(16);

		return s;
	}

	/**
	 * Returns a hexadecimal string which is exact representation of the bytes.
	 *
	 * @param src
	 *            source byte array
	 * @return a hexadecimal string which is exact representation of the bytes
	 */
	public static String toExactHexString(byte[] src) {
		BigInteger bi = new BigInteger(src);
		String s = bi.toString(16);

		return s;
	}

}
