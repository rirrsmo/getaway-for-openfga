package fr.rirrsmo.getaway.checker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add on a method to start an OpenFGA check before executing its content.
 * <br>
 * Values can use interpolation, using the bracket syntax.<br>
 * Interpolated values must match exactly a parameter name from the annotated method.
 * <br><br>
 * Examples of valid syntaxes include :
 * <ul>
 *     <li><code>user:anne</code></li>
 *     <li><code>user:{userId}</code></li>
 *     <li><code>{relationship}</code></li>
 *     <li><code>{objectType}:{objectId}</code></li>
 * </ul>
 *
 * The value of the interpolated argument will be the .toString() method result of the passed object
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OpenFGACheck {

    /**
     * Type and reference of the user, as it would be present in a tuple.
     * <b>Must</b> include the type, column ( : ), and user reference/id.
     * <br>
     * Interpolation is possible. See the {@link OpenFGACheck} documentation.
     */
    String user();

    /**
     * String representation of the relation name, as it would be present in a tuple.
     * <br>
     * Interpolation is possible. See the {@link OpenFGACheck} documentation.
     */
    String relation();
    /**
     * Type and reference of the object, as it would be present in a tuple.
     * <b>Must</b> include the type, column ( : ), and object reference/id.
     * <br>
     * Interpolation is possible. See the {@link OpenFGACheck} documentation.
     */
    String _object();

    // TODO Conditions and contextual tuples
}
