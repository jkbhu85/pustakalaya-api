package com.jk.pustakalaya.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtil {
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
	private static final String TMP_DIR_PATH = System.getProperty("java.io.tmpdir");

	private FileUtil() {
	}

	/**
	 * Returns byte array representing the file contents at the specified path.
	 *
	 * @param filePath
	 *            path of the file
	 * @return byte array representing the file contents at the specified path
	 * @throws Exception
	 *             if error occures in the process
	 */
	public static byte[] getBytes(String filePath) throws Exception {
		InputStream in = new FileInputStream(filePath);
		byte[] data = new byte[0];
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) > -1) {
			int start = data.length;
			data = Arrays.copyOf(data, data.length + len);

			for (int i = 0; i < len; i++) {
				data[start + i] = buffer[i];
			}

		}

		in.close();

		return data;
	}

	/**
	 * Returns a list of all non-hidden {@link File}(s) in the directory
	 * {@code dirPath}. Never returns {@code null}.
	 *
	 * @param dirPath
	 * @return list of all non-hidden {@link File}(s) in the directory
	 *         {@code dirPath}, never {@code null}
	 * @throws RuntimeException
	 *             if specified path does not exist or path does not represent a
	 *             directory or directory can not be read
	 */
	public static List<File> getAllFiles(String dirPath) throws RuntimeException {
		File dir = new File(dirPath);

		if (!dir.isDirectory()) {
			throw new RuntimeException("Sepcified path is not a directory. Path: " + dir.getAbsoluteFile());
		}

		if (!dir.canRead()) {
			throw new RuntimeException("Can not read the directory at path: " + dir.getAbsoluteFile());
		}

		return Arrays.asList(dir.listFiles());
	}

	/**
	 * Returns a path to a zip file containing all the files in the speified
	 * {@code fileList}. Returns {@code null} if {@code fileList} is {@code null} or
	 * empty.
	 *
	 * @param fileList
	 *            list of files to be zipped
	 * @return a path to a zip file containing all the files in the speified
	 *         {@code fileList}, returns {@code null} if {@code fileList} is
	 *         {@code null} or empty
	 * @throws Exception
	 *             if an error occures while zipping the files
	 */
	public static String zipFiles(List<File> fileList) throws Exception {
		if (fileList == null || fileList.isEmpty())
			return null;

		String zipFilePath = new StringBuffer().append(TMP_DIR_PATH).append(File.separator + "attachments_")
				.append(ID_GENERATOR.incrementAndGet()).append(".zip").toString();

		OutputStream out = new BufferedOutputStream(new FileOutputStream(zipFilePath));
		ZipOutputStream zip = new ZipOutputStream(out);
		byte[] buffer = new byte[1024];
		int len;

		zip.setLevel(4);

		for (File f : fileList) {
			zip.putNextEntry(new ZipEntry(f.getName()));
			InputStream in = new BufferedInputStream(new FileInputStream(f));

			while ((len = in.read(buffer)) > -1) {
				zip.write(buffer, 0, len);
			}

			in.close();
			zip.closeEntry();
		}

		zip.flush();
		zip.close();

		return zipFilePath;
	}

	/**
	 * Deletes the file at the specified path. File may not be deleted if it is a
	 * non-empty directory or there is no write permission.
	 *
	 * @param filePath
	 *            path of the file to be deleted
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);

		if (file.exists())
			file.delete();
	}
}