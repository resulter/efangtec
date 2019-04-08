package com.efangtec.web.controller;

import com.efangtec.web.annotation.DrugsType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annotation")
public class AnnotationTestController {
    @RequestMapping("/test")
    @DrugsType(type = "efangtec-a")
    public String test(){
        System.out.println("-*************************");
        return "1111";
    }

    @RequestMapping("/test")
    @DrugsType(type = "efangtec-b")
    public String test2(){
        System.out.println("-+++++++++++++++++++++++");
        return "22222";
    }
}
