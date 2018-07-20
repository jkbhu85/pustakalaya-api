package com.jk.ptk.app;

import static com.jk.ptk.util.ClassUtils.filterClasses;
import static com.jk.ptk.util.ClassUtils.listClassNames;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.istack.internal.logging.Logger;

/**
 * 
 * Initializes all the classes that have annotated with {@link DataLayerInitialized}
 * annotation. The system may shut down as per the contract of
 * {@link DataLayerInitialized}.
 * 
 * @author Jitendra
 *
 */

@Component
public class Initializer {
	private static final Logger log = Logger.getLogger(Initializer.class);

	private EntityManager entityManager;

	@Autowired
	public Initializer(EntityManager em) {
		this.entityManager = em;
	}

	public void initialize() {
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File root = new File(path);

		initializeDataLayer(root);
		
		log.info("All classes with annotation DataLayerInitialized are initialized.");
	}
	
	private void initializeDataLayer(File root) {
		List<String> classNameList;
		
		try {
			// list all classes in the project
			classNameList = listClassNames(root, "");
		} catch (Exception e) {
			log.severe("Error occurred while searching for classes.{}", e);
			return;
		}
		
		// predicate returns true if the class is annotated with
		// DataLayerInitialized, false otherwise
		Predicate<Class<?>> filter = (aClass) -> {
			if (aClass == null) return false;
			final Class<?> clazz = DataLayerInitialized.class;

			for (Annotation a : aClass.getAnnotations()) {
				if (clazz.isAssignableFrom(a.getClass())) {
					return true;
				}
			}

			return false;
		};
		
		List<Class<?>> classList;

		// filter out those that are not needed
		classList = filterClasses(classNameList, filter);
		
		// list of methods to be invoked
		List<Method> methodList = new LinkedList<>();

		for (Class<?> clazz : classList) {
			Method m;
			try {
				// find method in the class
				m = clazz.getDeclaredMethod("init", EntityManager.class);

				int modifiers = m.getModifiers();

				// method must be static
				if (!Modifier.isStatic(modifiers)) throw new Exception("Method is not static.");

				// all good, add method to list
				methodList.add(m);
			} catch (Exception e) {
				log.warning("Error occurred while finiding the method.", e);
			}
		}

		for (Method m : methodList) {
			try {
				// invoke the method
				// the first parameter is null because it is an static method
				m.setAccessible(true);
				m.invoke(null, entityManager);
			} catch (Exception e) {
				// catch as Exception because the method may throw some exception during
				// execution
				log.severe("Error occurred while executing the method. {}", e);
				log.info("Shutting down the system due to unrecoverable error.");

				// abnormal shut down of the system
				System.exit(-1);
			}
		}
	}
}
