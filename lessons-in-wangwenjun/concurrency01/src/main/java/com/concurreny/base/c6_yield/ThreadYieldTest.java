package com.concurreny.base.c6_yield;

import org.apache.commons.lang.math.IntRange;
import sun.lwawt.macosx.CSystemTray;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadYieldTest {

    /**
     * 线程的yield的作用：
     * - 1.属于一种启发式方法，其会提醒调度器我愿意放弃当前线程的CPU资源，如果CPU的资源不紧张，则会忽略这种提醒
     * - 2.调用yield方法会使当前线程从 RUNNING状态 切换到 RUNNABLE状态
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0,2).mapToObj(ThreadYieldTest::create)
                .forEach(Thread::start);


        TimeUnit.SECONDS.sleep(1);
    }

    private static Thread create(int index){
        Thread thread = new Thread(() -> {
            if (index == 0) {
                Thread.yield();
            }
            System.out.println(index);
        });
        thread.setDaemon(true);
        return thread;
    }
}
