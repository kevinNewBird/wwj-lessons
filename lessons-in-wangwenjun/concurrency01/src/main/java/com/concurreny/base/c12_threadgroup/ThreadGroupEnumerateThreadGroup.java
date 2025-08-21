package com.concurreny.base.c12_threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * 复制 ThreadGroup 数组
 * <br/>
 * 说明：
 * 1. int enumerate(Thread[] list)
 * 2. int enumerate(Thread[] list, boolean recurse)
 */
public class ThreadGroupEnumerateThreadGroup {

    public static void main(String[] args) throws InterruptedException {
        // 1.创建ThreadGroup(其父ThradGroup为当前线程的ThreadGroup，即为main线程的ThreadGroup)
        ThreadGroup myGroup1 = new ThreadGroup("MyGroup1");
        ThreadGroup myGroup2 = new ThreadGroup(myGroup1, "MyGroup2");

        TimeUnit.SECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        ThreadGroup[] list = new ThreadGroup[mainGroup.activeGroupCount()];
        int recurseSize = mainGroup.enumerate(list);
        // 2
        System.out.println(recurseSize);

        recurseSize = mainGroup.enumerate(list, false);
        // 1
        System.out.println(recurseSize);
    }
}
