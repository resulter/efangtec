package com.efangtec;

import com.efangtec.web.manager.TestManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HengruiWebApplicationTests {

    @Autowired
    TestManager testManager;
    @Test
    @RequestMapping("/test")
    public void test(){
        testManager.test();
    }

    @Test
    public void contextLoads() {
    }

}
