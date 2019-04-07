package com.efangtec.common.mq.service.impl;


import com.efangtec.common.mq.consumer.annotation.Consumer;
import com.efangtec.common.mq.domain.N1Record;
import com.efangtec.common.mq.mapper.N1RecordMapper;
import com.efangtec.common.mq.service.N1RecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * author:zjprevenge
 * time: 2016/6/28
 * copyright all reserved
 */
@Service("N1RecordService")
public class N1RecordServiceImpl implements N1RecordService {

    @Resource
    private N1RecordMapper n1RecordMapper;

    /**
     * 获取N1Record
     *
     * @param messageId
     * @return
     */
    public N1Record selectN1Record(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            return null;
        }
        return n1RecordMapper.selectN1Record(messageId);
    }

    /**
     * 添加N1Record
     *
     * @param n1Record
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int addN1Record(N1Record n1Record) {
        if (n1Record == null) {
            return 0;
        }
        return n1RecordMapper.addN1Record(n1Record);
    }

    /**
     * 删除N1Record
     *
     * @param timeStamp
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int deleteN1Record(Date timeStamp) {
        if (timeStamp == null) {
            return 0;
        }
        return n1RecordMapper.deleteN1Record(timeStamp);
    }
}
