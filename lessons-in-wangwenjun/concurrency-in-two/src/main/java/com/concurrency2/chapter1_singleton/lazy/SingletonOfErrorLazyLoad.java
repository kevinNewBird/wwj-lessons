package com.concurrency2.chapter1_singleton.lazy;

import org.springframework.context.annotation.Scope;

/**
 * Description: 单例模式的懒加载(错误方式) <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 11:13
 * @version: 1.0
 */
@Scope("prototype")
public class SingletonOfErrorLazyLoad {


    private static SingletonOfErrorLazyLoad instance;

    private SingletonOfErrorLazyLoad() {
        //empty
    }

    public static SingletonOfErrorLazyLoad getInstance() {
        //if multi thread to run ,can generate prototype instance.
        //多线程模式下,产生多个实例而不是我们期望的单例
        if (null == instance) {
            instance = new SingletonOfErrorLazyLoad();
        }
        return SingletonOfErrorLazyLoad.instance;
    }

}
