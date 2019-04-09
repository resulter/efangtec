package com.efangtec.apply.service;

public abstract  class AiDuoApplyService extends  AbstractApplyService {
    public AiDuoApplyService(ApplyService applyService) {
        super(applyService);
    }

    public abstract void delete();
}
