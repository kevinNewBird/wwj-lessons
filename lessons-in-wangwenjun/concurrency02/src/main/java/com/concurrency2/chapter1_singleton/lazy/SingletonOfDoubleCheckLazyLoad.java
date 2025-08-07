package com.concurrency2.chapter1_singleton.lazy;

/**
 * Description: 单例模式的懒加载(双重校验锁) <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/22 11:27
 * @version: 1.0
 */
public class SingletonOfDoubleCheckLazyLoad {

    private static SingletonOfDoubleCheckLazyLoad instance;

    private SingletonOfDoubleCheckLazyLoad() {
        //empty
    }

    //double check
    //Defect:maybe occur null pointer exception.
    //Reason: 可能会存在实例初始化,其内部一些属性还没有初始化完全就返回了(因为编译可能会有优化,重排序等)
    public static SingletonOfDoubleCheckLazyLoad getInstance() {

        if (null == instance) {
            //if multi thread in , one get the lock and generate a instance.
            //when it run over, another in and find instance is not null, will not init instance again.
            synchronized (SingletonOfDoubleCheckLazyLoad.class) {
                if (null == instance) {
                    instance = new SingletonOfDoubleCheckLazyLoad();
                }
            }
        }

        return SingletonOfDoubleCheckLazyLoad.instance;
    }
}
