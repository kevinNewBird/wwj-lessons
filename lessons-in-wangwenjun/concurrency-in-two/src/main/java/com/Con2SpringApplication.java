package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:  <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 10:40
 * @version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.concurrency2","com.classloader"})
public class Con2SpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(Con2SpringApplication.class, args);
    }
}
