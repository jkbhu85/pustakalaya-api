package com.jk.ptk.app;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.persistence.EntityManager;

/**
 * Marks a class to be initialized at system startup. The class should declare a
 * public static method that accepts an instance of {@link EntityManager} as
 * sole argument. If such a method is not declared by the class then this
 * annotation has no effect. However if the method exists and system fails to
 * execute it (may be an exception occurs) then the application is shut down.
 * 
 * <p>
 * The method declaration would look like this.
 * </p>
 * <pre>
 * public static void init(EntityManager em) {
 *   // code
 * }
 * </pre>
 * 
 * <p>
 * The return type of the method does not matter.
 * </p>
 * 
 * @author Jitendra
 *
 */

@Retention(RUNTIME)
@Target(TYPE)
public @interface DataLayerInitialized {

}
