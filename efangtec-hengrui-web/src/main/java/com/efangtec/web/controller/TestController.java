package com.efangtec.web.controller;

import com.efangtec.common.pojo.Result;
import com.efangtec.web.manager.TestManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    TestManager testManager;

    @RequestMapping("/test")
    public Result test(){
       return  testManager.test();
    }
}
