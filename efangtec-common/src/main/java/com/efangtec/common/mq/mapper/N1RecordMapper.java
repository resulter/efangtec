package com.efangtec.common.mq.mapper;


import com.efangtec.common.mq.domain.N1Record;
import org.apache.ibatis.annotations.*;

import java.util.Date;


@Mapper
public interface N1RecordMapper {


    /**
     * ������Ϣid��ȡ���Ѽ�¼
     *
     * @param messageId ��Ϣid
     * @return
     */
    @Select({" SELECT id,message_id,time_stamp  FROM n1_record  WHERE message_id = #{messageId} "})
    @Results(id = "messageMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "message_id", property = "messageId", javaType = String.class),
            @Result(column = "time_stamp", property = "timeStamp", javaType = Long.class)
    })
    N1Record selectN1Record(String messageId);

    /**
     * �����Ϣ���Ѽ�¼
     *
     * @param n1Record ���Ѽ�¼
     * @return
     */
    @Insert({"INSERT INTO n1_record(message_id,time_stamp)  VALUES (#{messageId},#{timeStamp})"})
    int addN1Record(N1Record n1Record);

    /**
     * ɾ�����Ѽ�¼
     *
     * @param timeStamp ʱ���
     * @return
     */
    @Delete({"  DELETE FROM n1_record   WHERE time_stamp &lt; #{timeStamp}"})
    int deleteN1Record(Date timeStamp);
}
