package com.concurrency2.chapter1_singleton.lazy;

/**
 * Description: 单例模式的懒加载(方法锁) <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 11:27
 * @version: 1.0
 */
public class SingletonOfMethodSyncronizedLazyLoad {

    private static SingletonOfMethodSyncronizedLazyLoad instance;

    private SingletonOfMethodSyncronizedLazyLoad() {
        //empty
    }

    //the synchronized lock is too heavy.
    public synchronized static SingletonOfMethodSyncronizedLazyLoad getInstance() {
        //if multi thread to run ,can generate prototype instance.
        //多线程模式下,产生多个实例而不是我们期望的单例
        if (null == instance) {
            instance = new SingletonOfMethodSyncronizedLazyLoad();
        }
        return SingletonOfMethodSyncronizedLazyLoad.instance;
    }
}
