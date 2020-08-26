package com.concurreny.demo;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/4/22 15:26
 * @Version: 1.0
 */
public class DemoTest {

    //默认核心池数量
    final static private int DEFAULT_CORE_POOL_SIZE = 50;

    //默认最大线程数量
    final static private int DEFAULT_MAX_POOL_SIZE = 200;

    //默认空闲线程存货时间
    final static private long DEFAULT_ALIVE_TIME_MILLS = 2_000;

    //默认最大任务队列
    final static private int DEFAULT_QUEUE_TASK_SIZE = 2_000;

    //超过核心线程池后,超出的线程池如果空闲时间超过2秒,将会被线程池回收
    //目前设置最大的任务队列为1000
    //默认拒绝策略:任务操作队列要求时,由调用该线程池的线程(即主线程)执行该任务
    private static final ThreadPoolExecutor DEFAULT_EXECUTOR = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE
            , DEFAULT_ALIVE_TIME_MILLS, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(DEFAULT_QUEUE_TASK_SIZE)
            , new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws InterruptedException {

        DEFAULT_EXECUTOR.execute(()->{
            System.out.println("hello world! thread:"+Thread.currentThread().getName());
        });
        Thread.sleep(10);
//        DEFAULT_EXECUTOR.setKeepAliveTime(100,TimeUnit.MILLISECONDS);
        DEFAULT_EXECUTOR.execute(()->{
            System.out.println("hello world! thread:"+Thread.currentThread().getName());
        });
        Thread.sleep(10);
        System.out.println("hello world!");
        if (DEFAULT_EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {

        }
        DEFAULT_EXECUTOR.shutdown();
    }
}
