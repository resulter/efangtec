package com.efangtec.passport.service;

public abstract  class AbstractPassportInfoService implements PassportInfoService {
    PassportInfoService passportInfoService;
    public AbstractPassportInfoService(PassportInfoService passportInfoService){
        this.passportInfoService = passportInfoService;
    }


    @Override
    public void add() {
        passportInfoService.add();
    }

    public void dddd(){

    }
}
