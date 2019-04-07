package com.efangtec.stock.entity.vo;

import com.efangtec.stock.entity.StockInfo;
import lombok.Data;

@Data
public class StockInfoVo extends StockInfo {
    String parentName;
}
