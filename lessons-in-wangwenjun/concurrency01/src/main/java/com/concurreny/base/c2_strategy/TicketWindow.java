package com.concurreny.base.c2_strategy;

import java.util.concurrent.TimeUnit;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 11:51
 * @Description:
 */
public class TicketWindow implements Runnable {
    private final static int MAX = 100;
    private int index = 1;


    /**
     * 共享资源无锁，将导致线程不可见，从而引发线程安全问题
     */
    public void run() {
//        synchronized (lock) {
            while (index <= MAX) {
                System.out.println(Thread.currentThread().getName() + "的号码是:" + index++);
                // 对index共享资源的操作，确保同时被多个线程操作
                sleep(1);
            }
//        }
    }

    private void sleep(long millis){
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
