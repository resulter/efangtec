package com.efangtec.followUp.service;


public abstract  class HengRuiFollowUpService extends AbstractFollowUpService {
    public HengRuiFollowUpService(FollowUpService followUpService) {
        super(followUpService);
    }

    public abstract void delete();
}
