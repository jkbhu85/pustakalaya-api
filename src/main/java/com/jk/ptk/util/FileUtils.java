package com.jk.ptk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class provides methods to work with files.
 *
 * @author Jitendra
 *
 */
public final class FileUtils {
	private static final AtomicLong ID_GENERATOR = new AtomicLong();
	private static final String TMP_DIR_PATH = System.getProperty("java.io.tmpdir");
	private static final int KILO_BYTE = 1024;

	private FileUtils() {
	}

	/**
	 * Returns {@code true} if the file is deleted successfully at {@code filePath},
	 * {@code false} otherwise.
	 *
	 * @param filePath
	 *                 the path of the file to be deleted
	 * @return {@code true} if the file is deleted successfully at {@code filePath},
	 *         {@code false} otherwise
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);

		return file.delete();
	}

	/**
	 * Returns {@code false} if one or more files could not be deleted successfully,
	 * {@code true} otherwise.
	 *
	 * @param files
	 *              list of files to be deleted, if list items are directories then
	 *              files are deleted recursively
	 * @return {@code false} if one or more files could not be deleted, {@code true}
	 *         otherwise
	 */
	public static boolean deleteFiles(List<File> files) {
		boolean status = true;

		if (files == null || files.isEmpty())
			return status;

		for (File file : files) {
			if (file.isDirectory() && file.list().length != 0) {
				List<File> childFiles = Arrays.asList(file.listFiles());

				deleteFiles(childFiles);
			}

			// if status is false for even once, this expression
			// will always evaluate to false indicating
			// one or more than one files were not
			// deleted successfully
			status = status & file.delete();
		}

		return status;
	}

	/**
	 * Returns {@code false} if one or more filePaths could not be deleted
	 * successfully, {@code true} otherwise.
	 *
	 * @param filePaths
	 *                  list of filePaths to be deleted, if list items are
	 *                  directories
	 *                  then filePaths are deleted recursively
	 * @return {@code false} if one or more filePaths could not be deleted,
	 *         {@code true} otherwise
	 */
	public static boolean deleteFilePaths(List<String> filePaths) {
		return deleteFiles(toFiles(filePaths));
	}

	/**
	 * Returns a list of all un-hidden files in the directory {@code dir}. Returns
	 * {@code null} if directory {@code dir} does not exist.
	 *
	 * @param dir
	 *            specified directory
	 * @return a list of all un-hidden files in the directory {@code dir} or
	 *         {@code null} if directory {@code dir} does not exist
	 * @throws IOException
	 *                     if {@code dir} is not a directory or is not readable
	 */
	public static List<File> getFiles(File dir) throws IOException {
		if (!dir.exists())
			return null;
		if (!dir.isDirectory())
			throw new IOException("Path is not a direcotry: " + dir.getAbsolutePath());
		if (!dir.canRead())
			throw new IOException("Can not read directory at path: " + dir.getAbsolutePath());

		List<File> list = new LinkedList<>();

		for (File file : dir.listFiles()) {
			list.add(file);
		}

		return list;
	}

	/**
	 * Returns a list of all un-hidden absolute filePaths in the directory path
	 * {@code dirPath}. Returns {@code null} if directory at the specified
	 * {@code dirPath} does not exist.
	 *
	 * @param dirPath
	 *                path of the specified directory
	 * @return a list of all un-hidden files in the directory {@code dirPath} or
	 *         {@code null} if directory at the specified {@code dirPath} does not
	 *         exist
	 * @throws Exception
	 *                   if {@code dirPath} is not a directory is can not be read
	 */
	public static List<String> getFilePaths(String dirPath) throws Exception {
		File dir = new File(dirPath);

		if (!dir.exists())
			return null;
		if (!dir.isDirectory())
			throw new IOException("Path is not a direcotry: " + dir.getAbsolutePath());
		if (!dir.canRead())
			throw new IOException("Can not read directory at path: " + dir.getAbsolutePath());

		List<String> list = new ArrayList<>();

		for (File file : dir.listFiles()) {
			list.add(file.getAbsolutePath());
		}

		return list;
	}

	/**
	 * Returns byte array of contents of the file represented by the
	 * {@code filePath}.
	 *
	 * @param filePath
	 *                 path of the file
	 * @return byte array of contents of the file represented by the
	 *         {@code filePath}
	 * @throws Exception
	 *                   if a problem occures in the process
	 */
	public static byte[] getBytes(String filePath) throws Exception {
		if (filePath == null)
			return null;

		return getBytes(new File(filePath));
	}

	/**
	 * Returns byte array representing contents of the {@code file}.
	 *
	 * @param file
	 *             the specified file
	 * @return byte array representing contents of the {@code file}
	 * @throws Exception
	 *                   if a problem occures in the process
	 */
	public static byte[] getBytes(File file) throws Exception {
		if (file == null)
			return null;

		InputStream in = new BufferedInputStream(new FileInputStream(file));
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
	 * Returns a zip file containing all the files in {@code files} or returns
	 * {@code null} if {@code files} is {@code null} or empty.
	 *
	 * @param files
	 *              list of files to be zipped
	 * @return a zip file containing all the files in {@code files} or returns
	 *         {@code null} if {@code files} is {@code null} or empty
	 * @throws Exception
	 *                   if an error occures while zipping the files
	 */
	public static File zipFiles(List<File> files) throws Exception {
		if (files == null || files.isEmpty())
			return null;

		// @formatter:off

		String zipFilePath = new StringBuffer().append(TMP_DIR_PATH).append(File.separator + "attachments_")
				.append(Math.abs(ID_GENERATOR.incrementAndGet())).append(".zip").toString();

		// @formatter:on

		return zipFiles(files, new File(zipFilePath));
	}

	/**
	 * Returns a zip file containing {@code files} or {@code null} if {@code files}
	 * is {@code null} or empty. If {@code zipFile} already exists then it will be
	 * overwritten.
	 *
	 * @param files
	 *                list of files to be zipped
	 * @param zipFile
	 *                file to be used as zip file target
	 * @return a zip file containing {@code files} or {@code null} if {@code files}
	 *         is {@code null} or empty
	 * @throws Exception
	 *                   if there is problem in zipping the files
	 */
	public static File zipFiles(List<File> files, File zipFile) throws Exception {
		if (files == null || files.size() == 0)
			return null;

		if (!zipFile.canWrite())
			throw new Exception("The path can not be written. Path: " + zipFile.getAbsolutePath());

		if (zipFile.isDirectory())
			throw new Exception("The path is a directory. Path: " + zipFile.getAbsolutePath());

		OutputStream out = new BufferedOutputStream(new FileOutputStream(zipFile));
		ZipOutputStream zipOut = new ZipOutputStream(out);
		byte[] buffer = new byte[4 * KILO_BYTE];
		int len;
		boolean exceptionOccurred = false;

		zipOut.setLevel(Deflater.BEST_COMPRESSION);

		try {
			for (File file : files) {
				if (!file.exists())
					throw new Exception("The file does not exist. Path: " + file.getAbsolutePath());
				if (file.isDirectory())
					throw new Exception(
							"The path represents a directory and hence can not be included into zip file. Path: "
									+ file.getAbsolutePath());
				if (!file.canRead())
					throw new Exception("The file can not be read. Path: " + file.getAbsolutePath());

				InputStream in = new BufferedInputStream(new FileInputStream(file));

				// append file into zip file
				zipOut.putNextEntry(new ZipEntry(file.getName()));

				while ((len = in.read(buffer)) > -1) {
					zipOut.write(buffer, 0, len);
				}

				in.close();
				zipOut.closeEntry();
			}

			zipOut.close();
		} catch (Exception e) {
			exceptionOccurred = true;
			throw e;
		} finally {
			zipOut.close();

			if (exceptionOccurred)
				zipFile.delete();
		}

		return zipFile;
	}

	/**
	 * Returns an absolute path of a zip file which contains {@code filePaths} or
	 * {@code null} if {@code filePaths} is {@code null} or empty. The zip file is
	 * created in the temporary directory provided by the system.
	 *
	 * @param filePaths
	 *                  list of filePaths to be zipped
	 * @return an absolute path of a zip file which contains {@code filePaths} or
	 *         {@code null} if {@code filePaths} is {@code null} or empty
	 * @throws Exception
	 *                   if there is problem in zipping the filePaths
	 */
	public static String zipFilePaths(List<String> filePaths) throws Exception {
		if (filePaths == null || filePaths.size() == 0)
			return null;

		File zipFile = zipFiles(toFiles(filePaths));
		return (zipFile == null ? null : zipFile.getAbsolutePath());
	}

	/**
	 * Returns an absolute path which points to the same file as {@code zipFilePath}
	 * of a zip file which contains {@code filePaths} or {@code null} if
	 * {@code filePaths} is {@code null} or empty. If a file already exists
	 * at{@code zipFilePath} then it will be overwritten.
	 *
	 * @param filePaths
	 *                    list of filePaths to be zipped
	 * @param zipFilePath
	 *                    destination path of the zip file
	 * @return an absolute path which points to the same file as {@code zipFilePath}
	 *         of a zip file which contains {@code filePaths} or {@code null} if
	 *         {@code filePaths} is {@code null} or empty
	 * @throws Exception
	 *                   if there is problem in zipping the filePaths
	 */
	public static String zipFilePaths(List<String> filePaths, String zipFilePath) throws Exception {
		if (filePaths == null || filePaths.size() == 0)
			return null;

		File zipFile = zipFiles(toFiles(filePaths), new File(zipFilePath));

		return (zipFile == null ? null : zipFile.getAbsolutePath());
	}

	/**
	 * Returns a list of {@link File} objects corressponding to file paths specified
	 * in {@code filePaths} or {@code null} if {@code filePaths} is {@code null}.
	 *
	 * @param filePaths
	 *                  list of file paths
	 * @return a list of {@link File} objects corressponding to file paths specified
	 *         in {@code filePaths} or {@code null} if {@code filePaths} is
	 *         {@code null}
	 */
	public static List<File> toFiles(List<String> filePaths) {
		if (filePaths == null)
			return null;

		List<File> files = new ArrayList<>();

		for (String filePath : filePaths) {
			files.add(new File(filePath));
		}

		return files;
	}

	public static void writeToFile(byte[] contents, String filePath) throws Exception {
		File file = new File(filePath);

		if (file.exists()) {
			if (file.isDirectory())
				throw new Exception("Can not write to a directory. Path: " + file.getAbsolutePath());
			if (!file.canWrite())
				throw new Exception("No write permission. Path: " + file.getAbsolutePath());
		}

		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));

		out.write(contents);

		out.close();
	}
}
