package com.efangtec.common.mq.mapper;


import com.efangtec.common.mq.domain.N1Record;
import org.apache.ibatis.annotations.*;

import java.util.Date;


@Mapper
public interface N1RecordMapper {


    /**
     * 根据消息id获取消费记录
     *
     * @param messageId 消息id
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
     * 添加消息消费记录
     *
     * @param n1Record 消费记录
     * @return
     */
    @Insert({"INSERT INTO n1_record(message_id,time_stamp)  VALUES (#{messageId},#{timeStamp})"})
    int addN1Record(N1Record n1Record);

    /**
     * 删除消费记录
     *
     * @param timeStamp 时间戳
     * @return
     */
    @Delete({"  DELETE FROM n1_record   WHERE time_stamp &lt; #{timeStamp}"})
    int deleteN1Record(Date timeStamp);
}
