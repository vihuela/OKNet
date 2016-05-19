/*
 *
 *   Copyright 2016 YunDi
 *
 *   The code is part of Yunnan, Shenzhen Branch of the internal architecture of YunDi source group
 *
 *   DO NOT DIVULGE
 *
 */

package ricky.oknet.modeinterface.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/************************************************************
 * Author:  Zhouml
 * Description:     // 模块描述
 * Date: 2016/3/15
 ************************************************************/
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface GET {
    String value() default "";
}
