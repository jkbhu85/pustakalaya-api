package com.jk.pustakalaya.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.jk.pustakalaya.config.App;

/**
 * This class is used to encrypt and generate base64 string of a password.
 * 
 * @author Jitendra
 *
 */
public class PasswordUtil {
	public static void main(String[] args) {
		try {
			String configDirPath = App.configDirPath();
			Properties props = new Properties();
			props.load(new FileInputStream(configDirPath + File.separator + "mail.properties"));
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
