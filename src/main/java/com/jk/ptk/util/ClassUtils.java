package com.jk.ptk.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ClassUtils {


	/**
	 * Returns list of class objects of all classes in {@code classNameList} that
	 * pass the specified {@code filter}. If a class for a class name in
	 * {@code classNameList} can not be found then no exception is thrown.
	 * 
	 * @param classNameList
	 *            list of pakcage qulified class names to be filtered
	 * @param filter
	 *            the specified filter
	 * @return list of class objects of all the classes in {@code classNameList}
	 *         that pass the specified {@code filter}
	 */
	public static List<Class<?>> filterClasses(List<String> classNameList, Predicate<Class<?>> filter) {
		if (classNameList == null) return null;

		List<Class<?>> cl = new ArrayList<>();

		for (String className : classNameList) {
			if (className.endsWith(".class")) className = className.substring(0, className.length() - ".class".length());
			try {
				Class<?> aClass = Class.forName(className);

				if (filter.test(aClass)) cl.add(aClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return cl;
	}

	/**
	 * Returns list of all classes in {@code root} directory and all subdirectories
	 * of {@code root} found while searching recursively. All class names are
	 * package qulified. The directory representated by {@code root} is not
	 * considered a package name.
	 * 
	 * <p>
	 * Let's say {@code root} represents the directory <some_path>/classes. This
	 * path is the root where all the java classes are stored. The caller should
	 * call this method with this directory and {@code prefix} either {@code null}
	 * or an empty string because all the classes found as direct child of this
	 * directory are in default package and hence have no package prefix.
	 * </p>
	 * 
	 * <p>
	 * Let's say {@code root} represents the directory <some_path>/classes/com. This
	 * path is the root where all the java classes are stored. The caller should
	 * call this method with this directory and {@code prefix} as "com." because all
	 * the classes found as direct child of this directory should have package
	 * prefix "com.".
	 * </p>
	 * 
	 * @param root
	 *            the directory where search begins recursively
	 * @param prefix
	 *            package prefix for the classes that are direct child of
	 *            {@code root} directory
	 * @return list of all classes in {@code root} directory and all subdirectories
	 *         of {@code root} found while searching recursively
	 * 
	 * @throws NullPointerException
	 *             if {@code root} is {@code null}.
	 * @throws IOException
	 *             if the path {@code root} is either not a directory or can not be
	 *             read.
	 * 
	 */
	public static List<String> listClassNames(File root, String prefix) throws NullPointerException, IOException {
		List<String> classList = new ArrayList<>();

		if (root == null) throw new NullPointerException("Root path is null.");

		if (!root.isDirectory()) throw new IOException("The path is not a direcotry. Path: " + root.getCanonicalPath());

		if (!root.canRead()) throw new IOException("The path can not be read. Path: " + root.getCanonicalPath());

		if (prefix == null) prefix = "";

		List<File> dirList = new LinkedList<>();

		for (File f : root.listFiles()) {
			if (f.isDirectory()) {
				dirList.add(f);
			} else if (f.getName().endsWith(".class")) {
				classList.add(prefix + f.getName());
			}
		}

		for (File dir : dirList) {
			classList.addAll(listClassNames(dir, prefix + dir.getName() + "."));
		}

		return classList;
	}
}
