package eu.activelogic.duck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If Interface or class is annotated with Identifying
 * then not all methods HAVE to be in the target object's type.
 * But only the ones that are also annotated with Identifying
 *
 * Ver 0.5 - Unsupported
 *
 * @author alex
 */

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Identifying {

}
