package com.concurrency2.chapter1_singleton.hungry;

/**
 * Description: 饿汉式单例模式 <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 11:13
 * @version: 1.0
 */
public class SingletonOfHungryType {


    //Defect: can't lazy load.
    //缺点:不能懒加载.
    private static final SingletonOfHungryType instance = new SingletonOfHungryType();

    private SingletonOfHungryType() {
        //empty
    }

    public static SingletonOfHungryType getInstance() {
        return instance;
    }

}
