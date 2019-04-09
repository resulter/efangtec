package com.efangtec.apply.service.impl;

import com.efangtec.apply.service.AiDuoApplyService;
import com.efangtec.apply.service.AnjinApplyService;
import com.efangtec.apply.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AiDuoApplyServiceImpl extends AiDuoApplyService {
    //https://blog.csdn.net/Jas000/article/details/78106804
    //@Lazy 默认是true，也就是说会懒加载，只有在被引用的时候才会加载，设为false会在容器初始化的时候急切的加载
    @Autowired
    @Lazy
    public AiDuoApplyServiceImpl(@Qualifier("aiDuoApplyServiceImpl") ApplyService applyService) {
        super(applyService);
    }

    @Override
    public void delete() {
        System.out.println("调用艾多申请方法");
    }


}
