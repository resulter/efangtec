package com.efangtec.dispensing.service;


public abstract  class AbstractDispensingService implements DispensingService {
    DispensingService dispensingService;
    public AbstractDispensingService(DispensingService dispensingService){
        this.dispensingService = dispensingService;
    }


    @Override
    public void add() {
        dispensingService.add();
    }

    public void dddd(){

    }
}
