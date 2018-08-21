package com.jk.ptk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jk.ptk.app.AppProps;

/**
 * This class provides methods for encryption and decryption.
 *
 * @author Jitendra
 *
 */
public final class CryptUtils {
	private static final String DES_CIPHER_TRANSFORMATION = "DES/ECB/PKCS5Padding";
	private static final String KEY_GENERATION_ALGORITHM_DES = "DES";
	private static final int DES_KEY_SIZE = 56; // fixed for DES
	private static final String KEY_FILE_NAME = "securityKey.ser";
	private static final String UTF_8 = "UTF-8";

	private static final String AES_CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM_AES = "AES";

	// prevent instantiation
	private CryptUtils() {
	}

	private static String getSecretKeyPath() {
		return AppProps.CONFIG_DIR_PATH + File.separator + KEY_FILE_NAME;
	}

	/**
	 * Returns decrypted contents of file. If {@code absolutePath} is {@code false},
	 * {@code fileName} is considered a file name and the file location is the
	 * directory which is same as value of {@link AppProps#NAME_ENV_VAR_CONFIG}.
	 *
	 * @param fileName
	 *            name or path of the file
	 * @param absolutePath
	 *            indicates if the path is an absolute path
	 * @return decrypted contents of file
	 * @throws Exception
	 *             if error occurred in the process
	 */
	public static byte[] decryptFromFile(String fileName, boolean absolutePath) throws Exception {
		String path;

		if (absolutePath)
			path = fileName;
		else
			path = AppProps.CONFIG_DIR_PATH + File.separator + fileName;

		byte[] encrypted = FileUtils.getBytes(path);
		byte[] decrypted = decrypt(encrypted);

		return decrypted;
	}

	/**
	 * Returns encrypted source.
	 *
	 * @param source
	 *            the data to be encrypted
	 * @return encrypted source
	 * @throws Exception
	 *             if an error occurred while encrypting the source
	 */
	public static byte[] encrypt(byte[] source) throws Exception {
		SecretKey secretKey = getSecretKey(getSecretKeyPath());

		Cipher cipher = Cipher.getInstance(DES_CIPHER_TRANSFORMATION);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] encryptedSource = cipher.doFinal(source);

		return encryptedSource;
	}

	/**
	 * Returns base64 string of encrypted {@code source}.
	 * 
	 * @param source the value to encrypt
	 * @param initialVector 16 byte string
	 * @param key 32 byte string
	 * @return base64 string of encrypted {@code source}
	 * @throws Exception if a problem occurs during encryption 
	 */
	public static String encryptIntoBase64(String source, String initialVector, String key) throws Exception {
		return Base64.getEncoder().encodeToString(encrypt(source.getBytes(), initialVector, key));
	}

	public static String decryptFromBase64(String base64, String initialVector, String key) throws Exception {
		byte[] source = Base64.getDecoder().decode(base64.getBytes());

		return new String(decrypt(source, initialVector, key));
	}

	public static byte[] encrypt(byte[] source, String initialVector, String key) throws Exception {
		IvParameterSpec ips = new IvParameterSpec(initialVector.getBytes(UTF_8));
		SecretKeySpec sks = new SecretKeySpec(key.getBytes(UTF_8), ALGORITHM_AES);

		Cipher cipher = Cipher.getInstance(AES_CIPHER_TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, sks, ips);

		byte[] decrypted = cipher.doFinal(source);

		return decrypted;
	}

	public static byte[] decrypt(byte[] source, String initialVector, String key) throws Exception {
		IvParameterSpec ips = new IvParameterSpec(initialVector.getBytes(UTF_8));
		SecretKeySpec sks = new SecretKeySpec(key.getBytes(UTF_8), ALGORITHM_AES);

		Cipher cipher = Cipher.getInstance(AES_CIPHER_TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, sks, ips);

		byte[] encrypted = cipher.doFinal(source);

		return encrypted;
	}

	/**
	 * Returns encrypted source.
	 *
	 * @param secretKeyPath
	 *            full path of the file where secret is stored or can be stored
	 * @param source
	 *            the data to be encrypted
	 * @return encrypted source
	 * @throws Exception
	 *             if an error occurred while encrypting the source
	 */
	public static byte[] encrypt(String secretKeyPath, byte[] source) throws Exception {
		// 1. get key
		SecretKey secretKey = getSecretKey(secretKeyPath);

		// 2. get cipher
		Cipher cipher = Cipher.getInstance(DES_CIPHER_TRANSFORMATION);

		// 3. initialize cipher
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		// 4. encrypt source
		byte[] encryptedSource = cipher.doFinal(source);

		// 5. return encrypted source
		return encryptedSource;
	}

	/**
	 * Returns the result of decryption.
	 *
	 * @param encrypted
	 *            the data to be decrypted
	 * @return the result of decryption
	 * @throws Exception
	 *             if an error occurs while decrypting the data
	 */
	public static byte[] decrypt(byte[] encrypted) throws Exception {
		SecretKey secretKey = loadKeyFromFile(getSecretKeyPath());

		Cipher cipher = Cipher.getInstance(DES_CIPHER_TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encrypted);
	}

	/**
	 * Returns the result of decryption.
	 *
	 * @param secretKeyPath
	 *            path to secret key using which the data was encrypted
	 * @param encrypted
	 *            the data to be decrypted
	 * @return the result of decryption
	 * @throws Exception
	 *             if an error occurred while decrypting the data
	 */
	public static byte[] dcrypt(String secretKeyPath, byte[] encrypted) throws Exception {
		SecretKey secretKey = loadKeyFromFile(secretKeyPath);

		Cipher cipher = Cipher.getInstance(DES_CIPHER_TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encrypted);
	}

	private static SecretKey getSecretKey(String secretKeyPath) throws Exception {
		SecretKey secretKey = null;

		// try to load secret key from path provided
		secretKey = loadKeyFromFile(secretKeyPath);

		if (secretKey != null) {
			System.out.println("Secret key loaded from file.");
		} else {
			System.out.println("No secret key found at the specified path.");

			// create secret key because one was not found on the path provided
			secretKey = generateKey(KEY_GENERATION_ALGORITHM_DES, DES_KEY_SIZE);
			System.out.println("New secret key generatred");

			// save secret key to the path provided for later use
			saveKeyToFile(secretKey, secretKeyPath);
			System.out.println("Secret key saved to file.");
		}

		return secretKey;
	}

	private static SecretKey generateKey(String algorithm, int keySize) throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(keySize);

		return kg.generateKey();
	}

	private static SecretKey loadKeyFromFile(String secretKeyPath)
			throws NullPointerException, IOException, ClassNotFoundException {
		File file = new File(secretKeyPath);

		// path does not exist or is a directory or can not be read then can not load
		// key
		if (!file.exists() || file.isDirectory() || !file.canRead())
			return null;

		SecretKey secretKey = null;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(secretKeyPath));

		secretKey = (SecretKey) ois.readObject();
		ois.close();

		return secretKey;
	}

	private static void saveKeyToFile(SecretKey key, String secretKeyPath) throws Exception {
		File file = new File(secretKeyPath);
		if (!file.exists()) {
			file.createNewFile();
			file.setWritable(true);
		}

		// path is a directory, can not write key
		if (file.isDirectory()) {
			throw new Exception("The path for secret key is not a file. Path: " + file.getAbsolutePath());
		}

		// path is not writable, can not write key
		if (!file.canWrite()) {
			throw new Exception("The file at specified path is not writable. Path: " + file.getAbsolutePath());
		}

		FileOutputStream fis = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fis);

		oos.writeObject(key);
		oos.close();
		fis.close();
	}
}
