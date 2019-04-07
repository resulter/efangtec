package com.efangtec.followUp.service.impl;

import com.efangtec.followUp.service.FollowUpService;
import com.efangtec.followUp.service.HengRuiFollowUpService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HengRuiFollowUpServiceImpl extends HengRuiFollowUpService {


    public HengRuiFollowUpServiceImpl(@Qualifier("hengRuiFollowUpServiceImpl") FollowUpService followUpService) {
        super(followUpService);
    }

    @Override
    public void delete() {

    }


}
