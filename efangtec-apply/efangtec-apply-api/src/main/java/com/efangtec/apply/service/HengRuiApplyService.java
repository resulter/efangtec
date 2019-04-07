package com.efangtec.apply.service;

public abstract class HengRuiApplyService extends  AbstractApplyService {
    public HengRuiApplyService(ApplyService applyService) {
        super(applyService);
    }

    public abstract void delete();
}
