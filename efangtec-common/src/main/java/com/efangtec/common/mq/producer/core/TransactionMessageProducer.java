package com.efangtec.common.mq.producer.core;

import com.efangtec.common.mq.domain.QMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class TransactionMessageProducer implements BeanFactoryAware, SmartInitializingSingleton, Ordered {

    private static final Logger log = LoggerFactory.getLogger(TransactionMessageProducer.class);

    private BeanFactory beanFactory;


    private ActiveMQConnectionFactory activeMQConnectionFactory;


    private MessageCallback messageCallback;


    private Connection connection;

    public TransactionMessageProducer() {
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ActiveMQConnectionFactory getActiveMQConnectionFactory() {
        return activeMQConnectionFactory;
    }

    public void setActiveMQConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        this.activeMQConnectionFactory = activeMQConnectionFactory;
    }

    public MessageCallback getMessageCallback() {
        return messageCallback;
    }

    public void setMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    public void afterSingletonsInstantiated() {
        this.messageCallback = (MessageCallback) beanFactory.getBean("messageCallback");
        this.activeMQConnectionFactory = (ActiveMQConnectionFactory) beanFactory.getBean("activeMQConnectionFactory");
        try {
            this.connection = activeMQConnectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            throw new RuntimeException("active broker connect connection error", e);
        }
    }

    /**
     * ������Ϣ��broker
     *
     * @param qMessage ��Ϣ
     */
    public void sendMessage(QMessage qMessage) {
        Session session = null;
        try {
            session = connection.createSession(qMessage.getTransaction() != 0, ActiveMQSession.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(qMessage.getDestination());
            javax.jms.MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(qMessage.getPersistent() != 0 ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
            MapMessage message = session.createMapMessage();
            message.setString("messageId", qMessage.getMessageId());
            message.setString("data", qMessage.getMessageContent());
            message.setString("timeStamp", String.valueOf(qMessage.getTimeStamp()));
            if (qMessage.getN2() != 0) {
                if (StringUtils.isNotBlank(qMessage.getBusinessMark())) {
                    message.setString("businessMark", qMessage.getBusinessMark());
                } else {
                    //���n2�������Ϣ��businessMarkΪ�գ��׳��쳣
                    throw new RuntimeException("n2 level message require businessMark not empty...");
                }
            }
            producer.send(message);

            if (qMessage.getTransaction() != 0) {
                session.commit();
            }
            messageCallback.onSuccess(qMessage.getMessageId());
        } catch (JMSException e) {
            log.error("send message to broker error:{}", e);
            messageCallback.onFail(e, qMessage.getMessageId());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    log.error("close session error:{}", e);
                }
            }
        }
    }
}
