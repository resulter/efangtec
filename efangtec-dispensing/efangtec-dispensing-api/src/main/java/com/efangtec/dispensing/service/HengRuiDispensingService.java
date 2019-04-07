package com.efangtec.dispensing.service;


public abstract  class HengRuiDispensingService extends AbstractDispensingService {
    public HengRuiDispensingService(DispensingService dispensingService) {
        super(dispensingService);
    }

    public abstract void delete();
}
