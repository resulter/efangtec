package com.efangtec.followUp.service;


public abstract  class AbstractFollowUpService implements FollowUpService {
    FollowUpService followUpService;
    public AbstractFollowUpService(FollowUpService followUpService){
        this.followUpService = followUpService;
    }


    @Override
    public void add() {
        followUpService.add();
    }

    public void dddd(){

    }
}
