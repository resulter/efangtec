package com.efangtec.common.mq.consumer.annotation;


import com.efangtec.common.mq.consumer.spring.MessageConsumerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MessageConsumerConfiguration.class)
public @interface EnableMessageQueue {
}
