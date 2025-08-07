package com.concurreny.base.c12_threadgroup;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/18 17:25
 * @version: 1.0
 */
public class ThreadGroupApi {

    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();


        ThreadGroup tg2 = new ThreadGroup(tg1,"TG2");
        Thread t2 = new Thread(tg2, "t2") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();

        System.out.println(tg1.activeCount());
        System.out.println(tg1.activeGroupCount());
        System.out.println(tg2.activeCount());
    }
}
