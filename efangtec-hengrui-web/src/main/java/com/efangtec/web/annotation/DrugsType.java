package com.efangtec.web.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DrugsType {

    /**
     * 标识版本号，从1开始
     */
    String type();

}