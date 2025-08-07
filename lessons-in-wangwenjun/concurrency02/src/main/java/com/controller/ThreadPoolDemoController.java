package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 23:56
 * @Version: 1.0
 */
@RestController
public class ThreadPoolDemoController {


    @RequestMapping("/demo.do")
    public String testDemo() {
        return "hello";
    }


    @RequestMapping("/testPoolWithoutDaemon")
    public String testPoolWithoutDaemon() {
        ThreadDemoTest2.testThreadPoolV1();
        return "create default thread without daemon!";
    }

    @RequestMapping("/testPoolKill1")
    public String testPoolWithDaemon() {
        new Thread(() -> {
            ThreadDemoTest2.testThreadPoolV2();
        }).start();

        return "create default thread with daemon!";
    }


    @RequestMapping("/testPoolKill2")
    public String testPoolWithDaemon2() {
        new Thread(() -> {
            ThreadDemoTest2.testThreadPoolV3();
        }).start();

        return "create default thread with daemon!force to kill him by reflect!";
    }


}
