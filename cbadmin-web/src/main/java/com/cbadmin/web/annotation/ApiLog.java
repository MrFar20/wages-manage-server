package com.cbadmin.web.annotation;

import java.lang.annotation.*;

/**
 * @createTime: 2021-03-11 12:19:23
 * @created by: mrwangx
 * @description: web请求日志注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

    /**
     * 日志的描述
     * @return
     */
    String value();

}
