package com.concurreny.base.c11_hook;

import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Description: 背景说明:
 * 线程的run方法的签名是不允许抛出异常的,那么如何将发生的异常通知到应用呢?
 * <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/18 15:19
 * @version: 1.0
 */
public class ThreadException {

    final static private int A = 10;
    final static private int B = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            int result = A / B;
        });


        t1.start();
        //Thread api提供一个可以抓捕不显示异常的方法
        t1.setUncaughtExceptionHandler((thread, e)->{
            System.out.println(e);
        });



    }

}

