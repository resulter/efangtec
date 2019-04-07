package com.efangtec.common.mq.producer.core;



public interface MessageCallback {


    void onSuccess(String messageId);

    void onFail(Exception e, String messageId);
}
