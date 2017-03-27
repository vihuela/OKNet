

package ricky.oknet.retrofit.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RUNTIME)
/**
 * 用于替换url中占位符
 */
public @interface Path {
    String value() default "";
}
