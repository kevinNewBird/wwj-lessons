package com.concurreny.base.c12_threadgroup;

/**
 * Description: 如何创建ThreadGroup <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/18 17:07
 * @version: 1.0
 */
public class ThreadGroupCreate {

    public static void main(String[] args) {
        //use the name
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(getThreadGroup().getName());
                        System.out.println(getThreadGroup().getParent());
//                        System.out.println(getThreadGroup().getParent().activeCount());
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup(tg1,"TG2");
        System.out.println(tg2.getName());
        System.out.println(tg2.getParent());
        //use the parent and group name
    }
}
