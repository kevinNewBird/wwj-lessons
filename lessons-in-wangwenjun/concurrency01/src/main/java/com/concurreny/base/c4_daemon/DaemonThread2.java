package com.concurreny.base.c4_daemon;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 16:48
 * @Description:主线程中,在一个新建线程中开启一个守护线程
 */
public class DaemonThread2 {

    /**
     * 设置守护线程 == main线程结束后,创建的守护线程也跟着结束
     * 守护线程的设置是相对的,  在线程a中创建线程b,b设置成daemon,那么线程a结束时线程b也就结束了
     */

    public static void main(String[] args) {

        Thread t = new Thread() {
            @Override
            public void run() {
                Thread innerThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            //模拟心跳检查
                            while (true) {
                                System.out.println("InnerThread thread finish running.");
                                Thread.sleep(10_000);
                                System.out.println("InnerThread thread finish done.");
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                innerThread.setDaemon(true);
                innerThread.start();

                try {
                    System.out.println("T thread finish running.");
                    Thread.sleep(1_000);
                    System.out.println("T thread finish done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        t.start();
        System.out.println("Main done");
    }
}
