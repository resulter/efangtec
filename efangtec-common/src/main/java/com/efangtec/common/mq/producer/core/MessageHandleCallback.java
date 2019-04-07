package com.efangtec.common.mq.producer.core;


import com.efangtec.common.mq.service.QMessageService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component(value = "messageCallback")
public class MessageHandleCallback implements MessageCallback {

    private static final Logger log = LoggerFactory.getLogger(MessageHandleCallback.class);

    @Resource
    private QMessageService qMessageService;


    public void onSuccess(String messageId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(messageId), "messageId must not empty");
        qMessageService.deleteQMessage(messageId);
    }


    public void onFail(Exception e, String messageId) {
        log.error("send tx messageId:{},error:{}", messageId, e);
    }
}
