package com.efangtec.apply.service;

public abstract  class AnjinApplyService extends  AbstractApplyService {
    public AnjinApplyService(ApplyService applyService) {
        super(applyService);
    }

    public abstract void delete();
}
