package com.jk.pustakalaya.util;

import java.io.Closeable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a shutdown hook. An object can register with it if it wants
 * itself to be closed when application shuts down.
 *
 * @author Jitendra
 *
 */
public final class AutoClose {
	private AutoClose() {
	}

	private static boolean closing = false;
	private static final Logger LOG = LoggerFactory.getLogger(AutoClose.class);
	private static final Runtime RUNTIME = Runtime.getRuntime();
	private static final List<Closeable> TASKS = Collections.synchronizedList(new LinkedList<Closeable>());
	private static final Thread SHUTDOWN_ACTIVITY = new Thread() {
		@Override
		public void run() {
			closing = true;
			LOG.debug("Performing activites on application shutdown.");

			for (Closeable c : TASKS) {
				try {
					c.close();
				} catch (Exception e) {
					LOG.error("Error in closing resource.{}", e);
				}
			}

			LOG.info("{} tasks were closed.", TASKS.size());
		}
	};
	// @formatter:on

	static {
		RUNTIME.addShutdownHook(SHUTDOWN_ACTIVITY);
	}

	/**
	 * Registers the specified object for autoclose on application shutdown. Calling
	 * this method has no effect if shut down activity has started.
	 *
	 * @param closeable
	 *            the specified object to register
	 */
	public static void register(Closeable closeable) {
		if (!closing) {
			TASKS.add(closeable);
		}
	}

	/**
	 * Unregisters the specified object from autoclose on application shutdown. This
	 * method has no effect if {@code closeable} was not registered. Also calling
	 * this method has no effect if shut down activity has started.
	 *
	 * @param closeable
	 *            the specified object to unregister
	 */
	public static void unregister(Closeable closeable) {
		if (!closing) {
			TASKS.remove(closeable);
		}
	}
}
