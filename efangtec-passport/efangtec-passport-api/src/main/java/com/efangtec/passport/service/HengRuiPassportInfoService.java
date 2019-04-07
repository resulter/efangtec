package com.efangtec.passport.service;

public abstract  class HengRuiPassportInfoService extends AbstractPassportInfoService {
    public HengRuiPassportInfoService(PassportInfoService passportInfoService) {
        super(passportInfoService);
    }

    public abstract void delete();
}
