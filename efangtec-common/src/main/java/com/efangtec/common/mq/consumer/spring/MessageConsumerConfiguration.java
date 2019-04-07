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
     * 静态变量注入 配置文件的值，注意去掉static修饰符
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
     * 创建消费者解析器
     *
     * @return
     */
    @Bean
    public MessageConsumerBeanPostProcessor messageConsumerBeanPostProcessor() {
        return new MessageConsumerBeanPostProcessor();
    }

    /**
     * 创建ActiveMQ连接工厂
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory(BROKER_USERNAME,BROKER_PASSWORD ,BROKER_URL);
    }

    /**
     * 创建线程池
     *
     * @return
     */
    @Bean
    public Executor executor() {
        return Executors.newFixedThreadPool(100);
    }

    /**
     * 创建消息幂等处理
     *
     * @return
     */
    @Bean
    public RepeatMessageHandle repeatMessageHandle() {
        return new RepeatMessageHandle();
    }
}
