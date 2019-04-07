package com.efangtec.common.mq.producer.core;

import com.alibaba.fastjson.JSONObject;

import com.efangtec.common.mq.domain.QMessage;
import com.efangtec.common.mq.service.QMessageService;
import com.efangtec.common.mq.producer.tx.MessageTransactionSynchronizationAdapter;
import com.efangtec.common.pojo.Result;
import com.efangtec.common.utils.MessageHolder;
import com.efangtec.common.utils.MessageUtils;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MessageProducer implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    @Resource
    private TransactionMessageProducer transactionMessageProducer;

    @Resource
    private QMessageService qMessageService;

    private String destName;

    private boolean transaction;

    private boolean persistent;

    private boolean n2;

    public TransactionMessageProducer getTransactionMessageProducer() {
        return transactionMessageProducer;
    }

    public void setTransactionMessageProducer(TransactionMessageProducer transactionMessageProducer) {
        this.transactionMessageProducer = transactionMessageProducer;
    }

    public QMessageService getqMessageService() {
        return qMessageService;
    }

    public void setqMessageService(QMessageService qMessageService) {
        this.qMessageService = qMessageService;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public boolean isN2() {
        return n2;
    }

    public void setN2(boolean n2) {
        this.n2 = n2;
    }

    /**
     * @param data
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void sendMessage(Result data) {
        Preconditions.checkArgument(data != null, "message must not be empty...");
        transactionSynchronize();
        QMessage message = convertMessage(data);
        int result = qMessageService.addQMessage(message);
        if (result != 0) {
            MessageHolder.set(message.getMessageId());
        }
    }

    /**
     * @param data
     * @return
     */
    private QMessage convertMessage(Result data) {
        QMessage qMessage = new QMessage();
        Date date = new Date();
        String messageId = MessageUtils.createMessageId(date);
        qMessage.setMessageId(messageId);
        qMessage.setMessageContent(JSONObject.toJSONString(data));
        qMessage.setDestination(destName);
        qMessage.setTimeStamp(date.getTime());
        qMessage.setN2(n2 ? 1 : 0);
        qMessage.setStatus(0);
        qMessage.setRetry(0);
        qMessage.setPersistent(persistent ? 1 : 0);
        qMessage.setTransaction(transaction ? 1 : 0);
        if (n2) {
            //businessMark
            // Preconditions.checkArgument(StringUtils.isNotBlank(data.get("businessMark")), "This queueName:" + destName + "is n2,businessMark must not be empty...");
            // qMessage.setBusinessMark(data.get("businessMark"));
        }
        return qMessage;
    }


    private void transactionSynchronize() {
        MessageTransactionSynchronizationAdapter synchronizationAdapter = new MessageTransactionSynchronizationAdapter();
        synchronizationAdapter.setqMessageService(qMessageService);
        synchronizationAdapter.setTransactionMessageProducer(transactionMessageProducer);
        TransactionSynchronizationManager.registerSynchronization(synchronizationAdapter);
    }

    public void afterPropertiesSet() throws Exception {

    }
}
