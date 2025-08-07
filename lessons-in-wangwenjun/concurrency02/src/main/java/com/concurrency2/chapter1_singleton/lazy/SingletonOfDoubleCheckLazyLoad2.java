package com.concurrency2.chapter1_singleton.lazy;

/**
 * Description: 单例模式的懒加载(双重校验锁) <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 11:27
 * @version: 1.0
 */
public class SingletonOfDoubleCheckLazyLoad2 {

    private static volatile SingletonOfDoubleCheckLazyLoad2 instance;

    private SingletonOfDoubleCheckLazyLoad2() {
        //empty
    }

    //double check add volatile
    //Defect:maybe occur null pointer exception.
    //Reason: 可能会存在实例初始化,其内部一些属性还没有初始化完全就返回了(因为编译可能会有优化,重排序等)
    //Resolution: 加上volatile关键字
    public static SingletonOfDoubleCheckLazyLoad2 getInstance() {

        if (null == instance) {
            //if multi thread in , one get the lock and generate a instance.
            //when it run over, another in and find instance is not null, will not init instance again.
            synchronized (SingletonOfDoubleCheckLazyLoad2.class) {
                if (null == instance) {
                    instance = new SingletonOfDoubleCheckLazyLoad2();
                }
            }
        }

        return SingletonOfDoubleCheckLazyLoad2.instance;
    }
}
