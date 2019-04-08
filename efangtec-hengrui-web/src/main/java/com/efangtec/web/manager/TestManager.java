package com.efangtec.web.manager;


import com.efangtec.apply.service.AnjinApplyService;
import com.efangtec.apply.service.HengRuiApplyService;
import com.efangtec.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class TestManager {
    //@Qualifier标注具体要注入哪个实现类
    @Autowired
    @Qualifier("hengRuiApplyServiceImpl")
    HengRuiApplyService hengRuiApplyService;

    @Autowired
    @Qualifier("anjinApplyServiceImpl")
    AnjinApplyService anjinApplyService;

    @RequestMapping("/test")
    public Result test(){
        hengRuiApplyService.delete();
        System.out.println("-*************************");
        anjinApplyService.delete();
        return null;
    }
}
