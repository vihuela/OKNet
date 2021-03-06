

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
 * 非json、string、bytes模式下，标记方法参数
 */
public @interface Param {
    String value() default "";
}
