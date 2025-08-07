package com.concurrency.jcu.atomic;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/1 17:14
 * @version: 1.0
 ***********************/
public class AtomicIntegerDetailsTest {


    //unsafe是不建议使用的 , 在jdk1.9是被拿掉了的

    public static void main(String[] args) throws NoSuchFieldException {

        // 1. create
        /*AtomicInteger atomic = new AtomicInteger();
        System.out.println("The default value of AtomicInteger: " + atomic.get());

        AtomicInteger atomic2 = new AtomicInteger(10);
        System.out.println("The default value of AtomicInteger: " + atomic2.get());*/

        // 2. set lazySet
        /*atomic.set(12);
        System.out.println("The set method: " + atomic.get());
        atomic.lazySet(13);*/

        // 3. get And add (CAS算法:compareAndSet)
        /*AtomicInteger oGSAtomic = new AtomicInteger(100);
        final int result = oGSAtomic.getAndAdd(10);
        System.out.println(result);
        System.out.println(oGSAtomic.get());*/

        // 4. mutil-thread
        /*final AtomicInteger mutilThreadAtomic = new AtomicInteger();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int mtResult = mutilThreadAtomic.addAndGet(1);
                Optional.of(Thread.currentThread().getName() + " of value:" + mtResult).ifPresent(System.out::println);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int mtResult = mutilThreadAtomic.addAndGet(1);
                Optional.of(Thread.currentThread().getName() + " of value:" + mtResult).ifPresent(System.out::println);
            }
        }).start();*/

        //5. compareAndSet
        /*final AtomicInteger aiAtomic = new AtomicInteger(10);
        final boolean flag = aiAtomic.compareAndSet(12, 20);
        System.out.println("compareAndSet method's result is:" + flag + ", the value is: " + aiAtomic.get());*/
        /*StringBuilder str = new StringBuilder();
        Integer strNo = 0;
        for (int i = 0; i < 5; i++) {
            strNo += 1;
            str.append(strNo).append(",");
        }
        System.out.println(str);*/
    }
}
