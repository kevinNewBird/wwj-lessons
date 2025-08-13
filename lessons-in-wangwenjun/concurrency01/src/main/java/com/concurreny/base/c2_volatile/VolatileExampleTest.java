package com.concurreny.base.c2_volatile;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class VolatileExampleTest {

    private int a = 0;
    // volatile关键字保证可见性和禁止重排（受限的原子性， 比如自增不支持原子性）
    // 本质是通过内存屏障来进行保证
    private volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        VolatileExampleTest shared = new VolatileExampleTest();

        CountDownLatch latch = new CountDownLatch(2);
        Thread t2 = new Thread(() -> {
            latch.countDown();
            shared.reader();
        });


        Thread t1 = new Thread(() -> {
            latch.countDown();
            shared.writer();
        });


        t1.start();
        t2.start();

        latch.await();

        t1.join();
        t2.join();

    }

    private void writer() {
        a = 1;
        flag = true;
        sleep(1);
    }

    private void reader() {
        if (flag) {
            int i = a;
            System.out.println(Thread.currentThread().getName() + ", a=" + a);
        }
    }

    private static void sleep(long mills){
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
