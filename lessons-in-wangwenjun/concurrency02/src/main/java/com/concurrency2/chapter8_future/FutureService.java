package com.concurrency2.chapter8_future;

import java.util.function.Consumer;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 15:32
 * @version: 1.0
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        //task是执行任务的单元 , 等待其将任务完成,将结果设置到Future中
        // ,然后外部Future通过调用get方法获取结果
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(() -> {
            T result = task.call();
            asynFuture.done(result);
        }).start();
        return asynFuture;
    }

    public <T> Future<T> submit(final FutureTask<T> task, final Consumer<T> consumer) {
        //task是执行任务的单元,等待其将任务完成,将结果设置到Future中
        // ,然后外部Future通过调用get方法获取结果
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(() -> {
            //执行任务
            T result = task.call();
            //任务完成,设置结果到Future(外部成员通过调用get获取结果)
            asynFuture.done(result);
            //加入回调机制(执行完成后,通知我的消息系统)
            consumer.accept(result);
        }).start();
        return asynFuture;
    }
}
