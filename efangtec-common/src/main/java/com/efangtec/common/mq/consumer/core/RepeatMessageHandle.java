package com.efangtec.common.mq.consumer.core;


import com.efangtec.common.mq.domain.N1Record;
import com.efangtec.common.mq.domain.N2Record;
import com.efangtec.common.mq.service.N1RecordService;
import com.efangtec.common.mq.service.N2RecordService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

public class RepeatMessageHandle {

    @Resource
    private N1RecordService n1RecordService;

    @Resource
    private N2RecordService n2RecordService;


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class,RuntimeException.class})
    public void repeatHandle(Map<String, Object> params,
                             MessageMethodHandle methodHandle,
                             boolean n2) throws Exception {
        String data = (String) params.get("data");
        String messageId = (String) params.get("messageId");
        Long timeStamp = Long.valueOf((String) params.get("timeStamp"));
        String topic = (String) params.get("topic");
        StringEvent event = new StringEvent(messageId, topic, data);
        if (!n2) {
            N1Record n1Record = n1RecordService.selectN1Record(messageId);
            if (n1Record == null) {
                n1Record = new N1Record();
                n1Record.setMessageId(messageId);
                n1Record.setTimeStamp(timeStamp);
                n1RecordService.addN1Record(n1Record);
                methodHandle.invokeMethod(event);
            }
        } else {
            N2Record n2Record = n2RecordService.selectN2Record(params);
            String businessMark = (String) params.get("businessMark");
            if (n2Record == null) {
                n2Record = new N2Record();
                n2Record.setBusinessMark(businessMark);
                n2Record.setTimeStamp(timeStamp);
                n2Record.setDestName(topic);
                n2RecordService.addN2Record(n2Record);
                methodHandle.invokeMethod(event);
            } else if (timeStamp > n2Record.getTimeStamp()) {
                n2Record.setTimeStamp(timeStamp);
                n2RecordService.updateN2Record(n2Record);
                methodHandle.invokeMethod(event);
            }
        }
    }

}
