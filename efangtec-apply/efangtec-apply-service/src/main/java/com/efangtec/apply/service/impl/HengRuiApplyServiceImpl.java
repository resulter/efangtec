package com.efangtec.apply.service.impl;

import com.efangtec.apply.service.ApplyService;
import com.efangtec.apply.service.HengRuiApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class HengRuiApplyServiceImpl  extends HengRuiApplyService {

    @Autowired
    @Lazy
    public HengRuiApplyServiceImpl(@Qualifier("hengRuiApplyServiceImpl") ApplyService applyService) {
        super(applyService);
    }

    @Override
    public void delete() {
        System.out.println("调用恒瑞申请方法" + HengRuiApplyServiceImpl.class);
    }


}
