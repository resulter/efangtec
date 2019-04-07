package com.efangtec.common.mq.mapper;

import com.efangtec.common.mq.domain.QMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 */
@Mapper
public interface QMessageMapper {

    /**
     * 根据messageId获取QMessage
     *
     * @param messageId
     * @return
     */
    @Select({"  SELECT  id,message_id,business_mark,message_content,status,retry,   destination,dest_type,time_stamp   FROM qmessage  WHERE  message_id = #{messageId}"})
    @Results(id = "messageMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "message_id", property = "messageId", javaType = String.class),
            @Result(column = "business_mark", property = "businessMark", javaType = String.class),
            @Result(column = "message_content", property = "messageContent", javaType = String.class),
            @Result(column = "status", property = "status", javaType = Integer.class),
            @Result(column = "retry", property = "retry", javaType = Integer.class),
            @Result(column = "destination", property = "destination", javaType = String.class),
            @Result(column = "time_stamp", property = "timeStamp", javaType = Long.class)
    })
    QMessage selectQMessageByMessageId(String messageId);

    /**
     * 添加消息
     *
     * @param qMessage 消息
     */

    @Insert({ "INSERT INTO qmessage(message_id,status,retry,message_content,destination,time_stamp) VALUES (#{messageId},#{status},#{retry},#{messageContent},#{destination},#{timeStamp}) " })
    int addQMessage(QMessage qMessage);


    /**
     * 更新消息
     *
     * @param qMessage
     */
    int updateQMessage(QMessage qMessage);

    /**
     * 删除消息
     *
     * @param messageId
     */
    @Delete({"  DELETE  FROM qmessage WHERE message_id = #{messageId} "})
    int deleteQMessage(String messageId);

    /**
     * 获取所有消息
     *
     * @return
     */
    @Select({"  SELECT id,message_id,business_mark,message_content,status,retry,destination,dest_type,time_stamp FROM qmessage WHERE time_stamp &lt;#{currentTime}-2000  ORDER BY time_stamp ASC "})
    @ResultMap("messageMap")
    List<QMessage> selectAllQMessage(Long currentTime);
}
