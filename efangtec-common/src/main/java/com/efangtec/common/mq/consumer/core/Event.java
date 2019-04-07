package com.efangtec.common.mq.consumer.core;


public interface Event<T> {

    String messageId();

    String topic();

    T content();
}
