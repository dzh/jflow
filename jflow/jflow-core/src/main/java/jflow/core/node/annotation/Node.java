/**
 * 
 */
package jflow.core.node.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jflow.core.node.NodeScope;

/**
 * <p>
 * <li>node basic properties</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 11:01:28 AM
 * @since 1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Node {

	String name() default "";

	boolean init() default false;

	NodeScope scope() default NodeScope.Flow;
}
