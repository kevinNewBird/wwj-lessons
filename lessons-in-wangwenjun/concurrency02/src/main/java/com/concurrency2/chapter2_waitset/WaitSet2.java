package com.concurrency2.chapter2_waitset;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 19:11
 * @version: 1.0
 */
public class WaitSet2 {
    final static private Object LOCK = new Object();


    /**
     * 思考: 既然线程的等待会释放掉持有锁, 再次唤醒会尝试重新获取锁,那么执行顺序会重头执行?
     */
    private static void work() {
        synchronized (LOCK) {
            System.out.println("Begin...");

            try {
                System.out.println("Thread will coming.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread will out.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread() {
            public void run() {
                work();
            }

        }.start();

        Thread.sleep(1000);
        synchronized (LOCK) {
            LOCK.notify();
        }

    }
}
