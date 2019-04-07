package com.efangtec.apply.service;

public abstract  class AbstractApplyService implements ApplyService {
    ApplyService applyService;
    public  AbstractApplyService(ApplyService applyService){
        this.applyService = applyService;
    }


    @Override
    public void add() {
        applyService.add();
    }

}
