package com.efangtec.stock.service;

public abstract  class AbstractStockService implements StockService {
    StockService stockService;
    public AbstractStockService(StockService stockService){
        this.stockService = stockService;
    }


    @Override
    public void add() {
        stockService.add();
    }

    public void dddd(){

    }
}
