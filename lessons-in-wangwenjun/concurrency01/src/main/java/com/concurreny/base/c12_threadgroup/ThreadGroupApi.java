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
        // 1.获取当前线程的group
        System.out.println(Thread.currentThread().getThreadGroup());
        // 2.定义一个新的group==tg1，tg1的默认父group为当前线程的group
        ThreadGroup tg1 = new ThreadGroup("TG1");
        // 3.创建一个线程，指定其group为tg1
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

        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        // 4
        System.out.println(mainGroup.activeCount());
        // 2
        System.out.println(mainGroup.activeGroupCount());
        // 2
        System.out.println(tg1.activeCount());
        // 1
        System.out.println(tg1.activeGroupCount());
        // 1
        System.out.println(tg2.activeCount());
        // 0
        System.out.println(tg2.activeGroupCount());
    }
}
