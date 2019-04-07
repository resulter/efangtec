package com.efangtec.web.manager;


import com.efangtec.apply.service.HengRuiApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestManager {
    @Autowired
    private HengRuiApplyService hengRuiApplyService;
}
