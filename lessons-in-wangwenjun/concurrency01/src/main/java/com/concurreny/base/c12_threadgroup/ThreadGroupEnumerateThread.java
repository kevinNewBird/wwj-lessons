package com.concurreny.base.c12_threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * 复制 Thread 数组
 * <br/>
 * 说明：
 * 1. int enumerate(Thread[] list)
 * 2. int enumerate(Thread[] list, boolean recurse)
 * 注意：
 * 1.enumerate方法获取的线程仅仅是个预估值，并不能百分百地保证当前group的活跃线程，
 *   比如在调用复制之后，某个线程结束了生命周期或者新的线程加入了进来，都会导致数据的不准确
 * 2.enumerate方法的返回值int相较Thread[]的长度更为真实，比如定义了数组长度的Thread数组，
 *   那么enumerate方法仅仅会将当前活跃的thread分别放进数组中，而返回值int则代表真实的数量，
 *   并非Thread数组的长度。
 */
public class ThreadGroupEnumerateThread {

    public static void main(String[] args) throws InterruptedException {
        // 1.创建一个ThreadGroup(其父ThradGroup为当前线程的ThreadGroup，即为main线程的ThreadGroup)
        ThreadGroup myGroup = new ThreadGroup("MyGroup");
        // 2.创建线程，并指定MyGroup
        new Thread(myGroup, ()->{
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
            }
        }, "MyThread").start();

        TimeUnit.SECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        Thread[] list = new Thread[mainGroup.activeCount()];
        int recurseSize = mainGroup.enumerate(list);
        // 3
        System.out.println(recurseSize);

        recurseSize = mainGroup.enumerate(list, false);
        // 2
        System.out.println(recurseSize);
    }
}
