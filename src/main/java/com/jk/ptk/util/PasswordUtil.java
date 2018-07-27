package com.jk.ptk.util;

import com.jk.ptk.app.AppProps;

/**
 * This class is used to encrypt and generate base64 string of a password.
 * 
 * @author Jitendra
 *
 */
public class PasswordUtil {
	public static void main(String[] args) {
		try {
			String key = AppProps.valueOf("ptk.mail.encryption.key");
			String iv = AppProps.valueOf("ptk.mail.encryption.iv");
			String pwd = "";

			String encrypted = CryptUtils.encryptIntoBase64(pwd, iv, key);
			System.out.println(encrypted);

			String original = CryptUtils.decryptFromBase64(encrypted, iv, key);
			System.out.println(original);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
