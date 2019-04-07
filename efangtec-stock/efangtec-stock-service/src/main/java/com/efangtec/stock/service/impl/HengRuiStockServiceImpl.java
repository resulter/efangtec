package com.efangtec.stock.service.impl;

import com.efangtec.stock.service.HengRuiStockService;
import com.efangtec.stock.service.StockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HengRuiStockServiceImpl extends HengRuiStockService {


    public HengRuiStockServiceImpl(@Qualifier("hengRuiStockServiceImpl") StockService stockService) {
        super(stockService);
    }

    @Override
    public void delete() {

    }


}
