package com.concurreny.base.c9_communication;

import java.util.stream.Stream;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/20 15:01
 * @Version: 1.0
 */
public class DifferenceOfSleepAndWait {

    private static final Object MONITOR = new Object();

    public static void main(String[] args) {
//        m1();
//        m2();
//        m3();

        Stream.of("T1", "T2").forEach(name->{
            new Thread(name){
                @Override
                public void run() {
                    m3();
//                    m4();
                }
            }.start();
        });

    }

    private static void m1() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void m2() {
        //3) . 使用sleep不需要定义一个synchronized同步方法 , 而wait需要
        //4) . sleep不需要唤醒 , wait需要(如果不唤醒,线程进入blocked状态)
        try {
            MONITOR.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void m3() {
        //3) . 使用sleep不需要定义一个synchronized同步方法 , 而wait需要
        //4) . sleep不需要唤醒 , wait需要(如果不唤醒,线程进入blocked状态)
        synchronized (MONITOR) {
            try {
                System.out.println("Thread "+Thread.currentThread().getName()+" enter.");
                MONITOR.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void m4() {
        //3) . 使用sleep不需要定义一个synchronized同步方法 , 而wait需要
        //4) . sleep不需要唤醒 , wait需要(如果不唤醒,线程进入blocked状态)
        synchronized (MONITOR) {
            try {
                System.out.println("Thread "+Thread.currentThread().getName()+" enter.");
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
