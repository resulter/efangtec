package com.efangtec.common.mq.producer.aspect;

import com.efangtec.common.mq.producer.annotation.ProducerMessage;
import com.efangtec.common.mq.producer.core.MessageProducer;
import com.efangtec.common.pojo.Result;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019-01-03.
 */
@Component
@Aspect
@Slf4j
public class EfangtecMessageAspect {


    @Autowired
    MessageProducer messageProducer;

    @AfterReturning(returning="result", pointcut="@annotation(message)")
    public void   after(Result result,ProducerMessage message) throws Throwable {
        log.debug("拦截message"+result);
        Preconditions.checkArgument(result != null, "message must not be empty...");
        messageProducer.setPersistent(true);
        messageProducer.setDestName(message.topic());
        messageProducer.setTransaction(true);
        messageProducer.setN2(false);
        messageProducer.sendMessage(result);
    }

}
