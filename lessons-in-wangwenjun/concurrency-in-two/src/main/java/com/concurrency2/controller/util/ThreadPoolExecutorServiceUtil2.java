package com.concurrency2.controller.util;


import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/9 21:34
 * @version: 1.0
 */
public class ThreadPoolExecutorServiceUtil2 {


    //默认核心池数量
    final static private int DEFAULT_CORE_POOL_SIZE = 10;

    //默认最大线程数量
    final static private int DEFAULT_MAX_POOL_SIZE = 20;

    //默认空闲线程存活时间
    final static private long DEFAULT_ALIVE_TIME_MILLS = 2_000;

    //默认最大任务队列
    final static private int DEFAULT_QUEUE_TASK_SIZE = 1_000;

    //默认线程池的关闭时间(单位:秒)
    final static private long DEFAULT_EXPIRE_TIME = 60;


    //超过核心线程池后,超出的线程池如果空闲时间超过2秒,将会被线程池回收
    //目前设置最大的任务队列为1000
    //默认拒绝策略:任务操作队列要求时,由调用该线程池的线程(即主线程)执行该任务
    private static final ThreadPoolExecutor DEFAULT_EXECUTOR = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE
            , DEFAULT_ALIVE_TIME_MILLS, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(DEFAULT_QUEUE_TASK_SIZE)
            , new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * Description: 获取默认的线程池 <BR>
     *
     * @param :
     * @return {@link ThreadPoolExecutor}
     * @author zhao.song    2020/7/10 10:04
     */
    public static ThreadPoolExecutor getInstance() {
        return DEFAULT_EXECUTOR;
    }

    /**
     * Description: 获取指定空闲线程的存活时间的线程池,这里的修改会导致全局线程池的参数变化 <BR>
     *
     * @param keepAliveTime:
     * @param unit:
     * @return {@link ThreadPoolExecutor}
     * @author zhao.song    2020/7/10 10:02
     */
    @Deprecated
    public static ThreadPoolExecutor getInstance(long keepAliveTime, TimeUnit unit) {
        DEFAULT_EXECUTOR.setKeepAliveTime(keepAliveTime, unit);
        return DEFAULT_EXECUTOR;
    }

    /**
     * Description: 归还线程 <BR>
     *
     * @param :
     * @return
     * @author zhao.song    2020/7/10 10:38
     */
    public static void shutdown() {
        shutdown(DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * Description: 归还线程 <BR>
     *
     * @param expireTime:
     * @param unit:
     * @return
     * @author zhao.song    2020/7/10 10:38
     */
    public static void shutdown(long expireTime, TimeUnit unit) {
        try {
            DEFAULT_EXECUTOR.shutdown();
            if (!DEFAULT_EXECUTOR.awaitTermination(expireTime, unit)) {
                //超时的时候向线程池发出中断
                DEFAULT_EXECUTOR.shutdownNow();
            }
            System.out.println("AwaitTermination Finished!");
        } catch (InterruptedException e) {
            DEFAULT_EXECUTOR.shutdownNow();
        }
    }
}
