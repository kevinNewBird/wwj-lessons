package com.concurrency.jcu.atomic;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * Description: Just-In-Time Compiler, 即时编译器 <BR>
 * @author: zhao.song
 * @date: 2020/9/1 16:45
 * @version: 1.0
 ***********************/
public class JITTest {

    //1.没有volatile关键字修饰: t1线程陷入死循环.但是如果在t1线程循环中任意输出,就会跳出循环
    private static boolean init = false;

    public static void main(String[] args) throws InterruptedException {

        final String s = "1213@";

        final String[] split = s.split("@");
        System.out.println(split.length);
        System.out.println(s.substring(0, s.length() - 1));
        System.out.println(Arrays.toString(split));

//        validate();
        /*final AtomicInteger atomic = new AtomicInteger(0);
        test111(atomic);       System.out.println(atomic.get());*/

    }

    private static void validate() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!init) {
                //任意输出,结束没有volatile修饰无法跳出的情况
                //原因说明: JIT优化:非volatile修饰的boolean作为一个条件,如果循环里是个空实现,会被修饰程while(true){}
//                System.out.println("");
            }
        });
        t1.start();

        Thread.sleep(1_000);

        Thread t2 = new Thread(() -> {
            init = true;
            System.out.println("Set init to ture.");
        });
        t2.start();
    }

    private static void test111(AtomicInteger atomicInteger) {
        atomicInteger.getAndAdd(1);
    }
}
