package com.efangtec.web.controller;

import com.efangtec.apply.service.AnjinApplyService;
import com.efangtec.apply.service.HengRuiApplyService;
import com.efangtec.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

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
