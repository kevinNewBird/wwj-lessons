package com.concurreny.base.c4_daemon;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 16:26
 * @Description:主线程中开启一个守护线程
 */
public class DaemonThread {


    public static void main(String[] args) {//main ruuning

        //ThreadGroup管理着线程 ,子线程如果没有设置守护或者join,就会在主线程结束后继续执行
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " running");
                    Thread.sleep(10_000);
                    System.out.println(Thread.currentThread().getName() + " done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };//new

        //runnable -> running|dead|blocked->terminated
        //设置守护线程 == main线程结束后,创建的守护线程也跟着结束
        //守护线程的设置是相对的,  在线程a中创建线程b,b设置成daemon,那么线程a结束时线程b也就结束了
        t.setDaemon(true);
        t.start();
        System.out.println(Thread.currentThread().isDaemon());
        //main terminated , threads is managed by threadGroup.
        System.out.println(Thread.currentThread().getName());
    }
}
