package com.efangtec.passport.service.impl;

import com.efangtec.passport.service.HengRuiPassportInfoService;
import com.efangtec.passport.service.PassportInfoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HengRuiPassportServiceImpl extends HengRuiPassportInfoService {


    public HengRuiPassportServiceImpl(@Qualifier("hengRuiPassportServiceImpl") PassportInfoService passportInfoService) {
        super(passportInfoService);
    }

    @Override
    public void delete() {

    }


}
