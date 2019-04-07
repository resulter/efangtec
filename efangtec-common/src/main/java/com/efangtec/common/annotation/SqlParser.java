package com.efangtec.common.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019-02-28.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SqlParser {

    /**
     * 过滤 SQL 解析，默认 false
     */
    boolean filter() default false;
}
