package com.efangtec.common.mq.consumer.core;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Executor;

public class MessageMethodHandle implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageMethodHandle.class);
    //@Consumer
    private Object bean;
    //@Listener
    private Method method;
    //broker
    private Session session;
    private String destination;
    private Executor executor;
    private boolean transaction;
    private boolean n2;
    private  RepeatMessageHandle repeatMessageHandle;
    public MessageMethodHandle() {
    }
    public  RepeatMessageHandle getRepeatMessageHandle() {
        return repeatMessageHandle;
    }

    public MessageMethodHandle setRepeatMessageHandle( RepeatMessageHandle repeatMessageHandle) {
        this.repeatMessageHandle = repeatMessageHandle;
        return this;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public MessageMethodHandle setTransaction(boolean transaction) {
        this.transaction = transaction;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public MessageMethodHandle setSession(Session session) {
        this.session = session;
        return this;
    }

    public Object getBean() {
        return bean;
    }

    public MessageMethodHandle setBean(Object bean) {
        this.bean = bean;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public MessageMethodHandle setMethod(Method method) {
        this.method = method;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public MessageMethodHandle setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Executor getExecutor() {
        return executor;
    }

    public MessageMethodHandle setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public boolean isN2() {
        return n2;
    }

    public MessageMethodHandle setN2(boolean n2) {
        this.n2 = n2;
        return this;
    }


    public void invokeMethod(Event event) throws Exception {
        method.invoke(bean, event);
    }


    public void onMessage(final Message message) {
        final MapMessage mapMessage = (MapMessage) message;
        try {
            final String messageId = mapMessage.getString("messageId");
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        if (messageId != null) {
                            final Map<String, Object> map = Maps.newHashMap();
                            map.put("data", mapMessage.getString("data"));
                            map.put("messageId", mapMessage.getString("messageId"));
                            map.put("timeStamp", mapMessage.getString("timeStamp"));
                            if (isN2()) {
                                map.put("topic", mapMessage.getString("topic"));
                                map.put("businessMark", mapMessage.getString("businessMark"));
                            }
                            repeatMessageHandle.repeatHandle(map, MessageMethodHandle.this, isN2());
                            if (transaction) {
                                session.commit();
                            } else {
                                message.acknowledge();
                            }
                        }
                    } catch (Exception e) {
                        log.error("handle message error:{}", e);
                    }
                }
            });

        } catch (Exception e) {
            log.error("handle message error: {}", e);
        }
    }
}
