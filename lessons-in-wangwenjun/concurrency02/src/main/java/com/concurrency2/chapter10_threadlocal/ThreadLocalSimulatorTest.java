package com.concurrency2.chapter10_threadlocal;

import java.util.Random;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:07
 * @version: 1.0
 ***********************/
public class ThreadLocalSimulatorTest {

    private static ThreadLocalSimulator<String> threadLocal = new ThreadLocalSimulator<String>() {
        @Override
        protected String initialValue() {
            return "default value";
        }
    };

    private static Random random = new Random(System.nanoTime());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            threadLocal.set("Thread-T1");
            try {
                Thread.sleep(random.nextInt(1_000));
                System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            threadLocal.set("Thread-T2");
            try {
                Thread.sleep(random.nextInt(1_000));
                System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("============================");
        System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());
    }
}
