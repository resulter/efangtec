package com.efangtec.common.mq.consumer.spring;

import com.efangtec.common.mq.consumer.core.RepeatMessageHandle;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
@ComponentScan("com.efangtec")
public class MessageConsumerConfiguration {
    private static String BROKER_URL;
    private static String BROKER_USERNAME;
    private static String BROKER_PASSWORD;

    /**
     * ��̬����ע�� �����ļ���ֵ��ע��ȥ��static���η�
     * @param mqUrl
     */
    @Value("${efangtec.mqUrl}")
    public void setBrokerUrl(String mqUrl) {
        BROKER_URL = mqUrl;
    }
    @Value("${efangtec.mqUsername}")
    public void setBrokerUsername(String brokerUsername) {
        BROKER_USERNAME = brokerUsername;
    }
    @Value("${efangtec.mqPassword}")
    public void setBrokerPassword(String brokerPassword) {
        BROKER_PASSWORD = brokerPassword;
    }

    /**
     * ���������߽�����
     *
     * @return
     */
    @Bean
    public MessageConsumerBeanPostProcessor messageConsumerBeanPostProcessor() {
        return new MessageConsumerBeanPostProcessor();
    }

    /**
     * ����ActiveMQ���ӹ���
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory(BROKER_USERNAME,BROKER_PASSWORD ,BROKER_URL);
    }

    /**
     * �����̳߳�
     *
     * @return
     */
    @Bean
    public Executor executor() {
        return Executors.newFixedThreadPool(100);
    }

    /**
     * ������Ϣ�ݵȴ���
     *
     * @return
     */
    @Bean
    public RepeatMessageHandle repeatMessageHandle() {
        return new RepeatMessageHandle();
    }
}
