package com.efangtec.common.mq.mapper;


import com.efangtec.common.mq.domain.N2Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface N2RecordMapper {


    N2Record selectN2RecordByMark(Map<String, Object> params);


    N2Record selectN2Record(Map<String, Object> params);


    int addN2Record(N2Record n2Record);


    int updateN2Record(N2Record n2Record);
}
