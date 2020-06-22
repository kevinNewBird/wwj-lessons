package com.concurreny.base.c10_booleanlock;

/**
 * Description: Synchronized锁获取到后, 无法被打断,导致其它的线程无法获取 <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 16:31
 * @Version: 1.0
 */
public class SynchronizedProblem {


    /**
     * Description: Synchronized的打断 <BR>
     * <p>这里所谓的打断是指正常运行过程中的打算 , 即线程没有处于 [休眠,等待] 等状态时的打断</p>
     */

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            SynchronizedProblem.run();
        }, "t1").start();

        Thread.sleep(10_000);

        Thread t2 = new Thread(() -> {

            SynchronizedProblem.run();
        }, "t2");

        t2.start();

        //尝试去中断等待执行的线程
        Thread.sleep(2000);
        t2.interrupt();
        System.out.println(t2.isInterrupted());


    }


    private synchronized static void run() {
        System.out.println(Thread.currentThread().getName());
        while (true) {

        }

    }

}
