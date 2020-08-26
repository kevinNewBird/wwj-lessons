package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.controller")
public class ConcurrenyInOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrenyInOneApplication.class, args);
    }

}
