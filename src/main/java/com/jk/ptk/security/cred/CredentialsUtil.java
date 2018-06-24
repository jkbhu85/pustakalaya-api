package com.jk.ptk.security.cred;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CredentialsUtil {
	private static Logger log = LoggerFactory.getLogger(CredentialsUtil.class);
	private static final HashGenerator hashGenerator = new HashGenerator();
	private static final PasswordSaltGenerator pwdSaltGenerator = new PasswordSaltGenerator();

	public static String getPasswordHash(String password, String salt, int passwordVersion) {
		return hashGenerator.getPasswordHash(password, salt, passwordVersion);
	}

	public static String getPasswordSalt() {
		return pwdSaltGenerator.getPasswordSalt();
	}

	private static class PasswordSaltGenerator {
		private SecureRandom sr = new SecureRandom();

		private String getPasswordSalt() {
			byte[] bytes = new byte[8];

			// get secure random bits
			sr.nextBytes(bytes);

			return new String(bytes);
		}
	}

	private static class HashGenerator {
		private MessageDigest md1;
		private MessageDigest md2;

		private HashGenerator() {
			try {
				md1 = MessageDigest.getInstance("SHA-256");
				md2 = MessageDigest.getInstance("SHA-384");
			} catch (NoSuchAlgorithmException e) {
				log.error("Error in getting hash algorithm: {}", e);
			}
		}

		private String getPasswordHash(String password, String salt, int passwordVersion) {
			MessageDigest md = getAlgorithm(passwordVersion);
			byte[] msg = (password + salt).getBytes();

			md.update(msg);
			byte[] digest = md.digest();

			return toHexString(digest);
		}

		private MessageDigest getAlgorithm(int passwordVersion) {
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

		private String toHexString(byte[] bytes) {
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

}
