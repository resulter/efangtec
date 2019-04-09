package com.efangtec.web.manager;


import com.efangtec.apply.service.*;
import com.efangtec.common.pojo.Result;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Component
public class ApplyManager {

    @Autowired
    @Qualifier("hengRuiApplyServiceImpl")
    HengRuiApplyService hengRuiApplyService;

    @Autowired
    @Qualifier("anjinApplyServiceImpl")
    AnjinApplyService anjinApplyService;

    @Autowired
    @Qualifier("aiRuiNiApplyServiceImpl")
    AiRuiNiApplyService aiRuiNiApplyService;

    @Autowired
    @Qualifier("aiDuoApplyServiceImpl")
    AiDuoApplyService aiDuoApplyService;

    @Autowired
    @Qualifier("common")
    ApplyService applyService;



    public Result test1() {
        Result result = new Result();
        result.setMessage("11111");
        aiDuoApplyService.delete();
        return result;
    }

    public Result test2() {
        Result result = new Result();
        result.setMessage("22222");
        aiRuiNiApplyService.delete();
        return result;
    }
    public Result test3() {
        Result result = new Result();
        result.setMessage("22222");
        applyService.add();
        return result;
    }
}
