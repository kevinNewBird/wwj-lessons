package com.concurrency2.chapter1_singleton.lazy;

/**
 * Description: 使用枚举类型 <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 17:15
 * @version: 1.0
 */
public class SingletonOfUseEnum {

    private SingletonOfUseEnum() {

    }


    private enum Singleton {
        INSTANCE;

        private final SingletonOfUseEnum instance;

        Singleton() {
            instance = new SingletonOfUseEnum();
        }

        public SingletonOfUseEnum getInstance() {
            return instance;
        }
    }

    public static SingletonOfUseEnum getInstance() {
        return Singleton.INSTANCE.getInstance();
    }
}
