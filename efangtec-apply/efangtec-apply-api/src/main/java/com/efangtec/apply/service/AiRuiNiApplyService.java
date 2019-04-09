package com.efangtec.apply.service;

public abstract  class AiRuiNiApplyService extends  AbstractApplyService {
    public AiRuiNiApplyService(ApplyService applyService) {
        super(applyService);
    }

    public abstract void delete();
}
