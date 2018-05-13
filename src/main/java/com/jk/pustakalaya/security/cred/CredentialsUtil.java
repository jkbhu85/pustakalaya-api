package com.jk.pustakalaya.security.cred;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CredentialsUtil {
	private static MessageDigest md1;
	private static MessageDigest md2;

	static {
		try {
			md1 = MessageDigest.getInstance("SHA-256");
			md2 = MessageDigest.getInstance("SHA-384");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String getPasswordHash(String password, String salt, int passwordVersion) {
		MessageDigest md = getAlgorithm(passwordVersion);
		byte[] msg = (password + salt).getBytes();

		md.update(msg);
		byte[] digest = md.digest();

		return toHexString(digest);
	}

	private static MessageDigest getAlgorithm(int passwordVersion) {
		try {
			if (passwordVersion == 1)
				return (MessageDigest) md1.clone();

			if (passwordVersion == 2)
				return (MessageDigest) md2.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String toHexString(byte[] bytes) {
		StringBuffer hexString = new StringBuffer();
		String hex;

		for (byte b : bytes) {
			hex = Integer.toHexString(b & 0xff);
			if (hex.length() == 1) {
				hexString.append("0").append(hex);
			} else {
				hexString.append(hex);
			}
		}

		return hexString.toString();
	}
}
