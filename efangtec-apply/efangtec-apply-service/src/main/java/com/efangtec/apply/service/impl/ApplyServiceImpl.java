package com.efangtec.apply.service.impl;

import com.efangtec.apply.service.ApplyService;
import org.springframework.stereotype.Service;

@Service("common")
public class ApplyServiceImpl implements ApplyService {
    @Override
    public void add() {
        System.out.println("调用申请通用方法");
    }
}
