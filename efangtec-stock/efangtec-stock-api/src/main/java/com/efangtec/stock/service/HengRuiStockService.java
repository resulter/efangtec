package com.efangtec.stock.service;

public abstract  class HengRuiStockService extends AbstractStockService {
    public HengRuiStockService(StockService stockService) {
        super(stockService);
    }

    public abstract void delete();
}
