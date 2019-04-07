package com.efangtec.common.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019-02-19.
 */
@Configuration
@MapperScan({"com.efangtec.common.mq.mapper"})
public class SpringConfig {

   /* @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory("","", "tcp://localhost:61616");
    }*/
}
