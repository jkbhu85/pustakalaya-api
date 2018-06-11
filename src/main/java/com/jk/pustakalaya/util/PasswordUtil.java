package com.jk.pustakalaya.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * This is a temporary class to test some of the functionality of encryption and
 * decryption implemented in this project.
 * 
 * @author Jitendra
 *
 */
public class PasswordUtil {
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("/home/jitendra/dev/ptk_config/mail.properties"));
			String key = props.getProperty("ptk.mail.encryption.key");
			String iv = props.getProperty("ptk.mail.encryption.iv");
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
