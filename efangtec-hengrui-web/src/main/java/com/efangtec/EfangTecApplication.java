package com.efangtec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.efangtec")
public class EfangTecApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfangTecApplication.class, args);
    }
}
