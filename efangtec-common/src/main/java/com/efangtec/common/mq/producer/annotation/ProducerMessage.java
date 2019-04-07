package com.efangtec.common.mq.producer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 发送短信&推送消息 spel表达式
 * Created by Administrator on 2019-01-24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProducerMessage {
    //消息队列的名称
    String topic() default "";
}
