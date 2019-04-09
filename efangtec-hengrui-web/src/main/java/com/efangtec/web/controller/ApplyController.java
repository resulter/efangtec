package com.efangtec.web.controller;

import com.efangtec.common.pojo.Result;
import com.efangtec.web.annotation.DrugsType;
import com.efangtec.web.manager.ApplyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class ApplyController {

    @Autowired
    ApplyManager applyManager;



    @RequestMapping("/delete")
    @DrugsType(type = "ef-ni")
    public Result test1(String key){
        Result result = applyManager.test1();
        return  result;
    }


    @RequestMapping("/delete")
    @DrugsType(type = "ef-ai")
    public Result test2(String key){
        Result result = applyManager.test2();
        return  result;
    }
    @RequestMapping("/add")
    public Result test3(String key){
        Result result = applyManager.test3();
        return  result;
    }
    @RequestMapping("/city")
    public String test4(String key){
        return  "city";
    }
}
