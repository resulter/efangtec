package com.efangtec.apply.service.impl;

import com.efangtec.apply.service.AnjinApplyService;
import com.efangtec.apply.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AnjinApplyServiceImpl extends AnjinApplyService {
    //https://blog.csdn.net/Jas000/article/details/78106804
    @Autowired
    @Lazy
    public AnjinApplyServiceImpl(@Qualifier("anjinApplyServiceImpl") ApplyService applyService) {
        super(applyService);
    }

    @Override
    public void delete() {
        System.out.println("调用安进方法");
    }


}
