package com.concurrency2.chapter2_waitset;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Description: 线程调用wait方法时有一个虚拟的概念waitset <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 18:38
 * @version: 1.0
 */
public class WaitSet {

    final static private Object LOCK = new Object();


    /**
     * 1. 所有的对象都会有一个wait set, 用来存放调用了该对象wait方法之后进入block状态线程
     * 2. 线程被notify之后不一定立即执行
     * 3. 线程中wait set 中被唤醒顺序不一定是FIFO.
     * 4. 线程被唤醒后必须重新获取锁
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {


        IntStream.rangeClosed(1, 10).forEach(i -> {
            new Thread(String.valueOf(i)) {
                @Override
                public void run() {
                    synchronized (LOCK) {
                        try {
                            Optional.of(Thread.currentThread().getName() + " will come to wait set.")
                                    .ifPresent(System.out::println);

                            LOCK.wait();

                            Optional.of("> " + Thread.currentThread().getName() + " will leave to wait set.")
                                    .ifPresent(System.out::println);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });

        Thread.sleep(4_000);

        IntStream.rangeClosed(1, 10).forEach(i -> {
            synchronized (LOCK) {
                LOCK.notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
