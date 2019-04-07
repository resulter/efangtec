package com.efangtec.common.mq.consumer.core;

import com.efangtec.common.mq.consumer.core.Event;

public class StringEvent implements Event<String> {

    private String messageId;

    private String topic;

    private String data;

    public StringEvent() {
    }

    public StringEvent(String messageId, String topic, String data) {
        this.messageId = messageId;
        this.topic = topic;
        this.data = data;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return
     */
    public String messageId() {
        return messageId;
    }

    /**
     *
     * @return
     */
    public String topic() {
        return topic;
    }

    /**
     * @return
     */
    public String content() {
        return data;
    }
}
