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
public class DemoController {


    @RequestMapping("/demo.do")
    public String testDemo() {
        return "hello";
    }


    @RequestMapping("/testPoolWithoutDaemon")
    public String testPoolWithoutDaemon() {
        ThreadDemoTest.testThreadPoolV1();
        return "create default thread without daemon!";
    }

    @RequestMapping("/testPoolWithDaemon")
    public String testPoolWithDaemon() {
        ThreadDemoTest.testThreadPoolV2();
        return "create default thread with daemon!";
    }


}
