package com.concurrency2.chapter10_threadlocal;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/14 11:52
 * @version: 1.0
 */
public class ThreadLocalSimpleTest {


    private static ThreadLocal threadLocal = new ThreadLocal();


    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("Alex");
        Thread.sleep(1000);
        System.out.println(threadLocal.get());
    }
}
