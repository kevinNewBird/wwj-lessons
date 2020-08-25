package com.concurrency2.chapter1_singleton.lazy;

/**
 * Description: 静态内部类的方式 <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 17:10
 * @version: 1.0
 */
public class SingletonOfOptimum {


    //优势: 既能保证懒加载,又能保证高效,还不用加锁

    private SingletonOfOptimum() {
    }

    private static class InstanceHolder {
        //static只会被初始化一次 , 并且只有在调用的时候会赋值(实现了懒加载)
        private final static SingletonOfOptimum instance = new SingletonOfOptimum();
    }

    public static SingletonOfOptimum getInstance() {
        return InstanceHolder.instance;
    }
}
