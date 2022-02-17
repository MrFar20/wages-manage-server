package com.cbadmin.web.annotation;

import com.cbadmin.common.constant.enums.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Auth {

    UserType[] value() default {};

}
