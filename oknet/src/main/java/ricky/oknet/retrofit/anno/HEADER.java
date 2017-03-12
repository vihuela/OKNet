

package ricky.oknet.retrofit.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
/**
 * 需要设置更多header，可在callBack的onBefore中得到BaseRequest设置
 */
public @interface HEADER {

    String key() default "";

    String value() default "";

}
