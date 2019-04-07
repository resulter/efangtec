package com.efangtec.dispensing.service.impl;

import com.efangtec.dispensing.service.DispensingService;
import com.efangtec.dispensing.service.HengRuiDispensingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HengRuiDispensingServiceImpl extends HengRuiDispensingService {


    public HengRuiDispensingServiceImpl(@Qualifier("hengRuiDispensingServiceImpl") DispensingService dispensingService) {
        super(dispensingService);
    }

    @Override
    public void delete() {

    }


}
