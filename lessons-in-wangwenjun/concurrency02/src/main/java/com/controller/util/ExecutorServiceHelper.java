package com.controller.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/15 13:19
 * @version: 1.0
 */
public class ExecutorServiceHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceHelper.class);
    private String name = ExecutorServiceHelper.class.getName();

    //默认线程池大小为可用CPU数量的两倍
    public static final int DEFAULT_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;
    private ThreadPoolExecutor executor;
    private DelayQueue<TimeLimitFuture<?>> timeLimitJobQueue = new DelayQueue<TimeLimitFuture<?>>();
    private ExecutorService delayQueueExecutor = new ThreadPoolExecutor(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0L
            , TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(name + "---delay thread");
            return thread;
        }
    });

    private ExecutorServiceHelper(ThreadPoolExecutor executor) {
        init(ExecutorServiceHelper.class.getName(), executor, null);
    }

    private ExecutorServiceHelper(String name, ThreadPoolExecutor executor, Thread.UncaughtExceptionHandler handler) {
        init(name, executor, handler);
    }

    private void init(String name, ThreadPoolExecutor executor, Thread.UncaughtExceptionHandler handler) {
        this.executor = executor;
        this.name = name;
        executor.setThreadFactory(getThreadFactory(handler));
        initDelayMonitor();
    }

    private void initDelayMonitor() {
        delayQueueExecutor.execute(() -> {
            while (!Thread.interrupted()) {
                TimeLimitFuture<?> timeLimitFuture = null;
                try {
                    timeLimitFuture = timeLimitJobQueue.take();
                    Object result = timeLimitFuture.future.get(1, TimeUnit.MILLISECONDS);
                    if (timeLimitFuture.callbackWarp != null) {
                        timeLimitFuture.callbackWarp.setResult(result);
                    }
                } catch (Exception e) {
                    if (e instanceof InterruptedException || e instanceof ExecutionException || e instanceof TimeoutException) {
                        timeLimitFuture.future.cancel(true);
                        if (stopTimeOutThread) {
                            killThread((FutureTask<?>) timeLimitFuture.future);
                            logger.error("future.kill");
                        } else {
                            logger.error("future.cancel");
                        }
                        timeLimitFuture = null;
                    } else {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
    }

    private void killThread(FutureTask<?> submit) {
        try {
            // 利用反射，强行取出正在运行该任务的线程
            Field runner = submit.getClass().getDeclaredField("runner");
            runner.setAccessible(true);
            Thread execThread = (Thread) runner.get(submit);
            execThread.stop();
            execThread = null;
            // 为了防止句柄泄漏，这里催促jvm进行gc回收，那是因为进行gc回收时，可以收回被stop的线程所占用的句柄
            System.gc();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private ThreadFactory getThreadFactory(Thread.UncaughtExceptionHandler handler) {
        return new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(name + "---job thread---" + atomicInteger.getAndIncrement());
                if (handler != null) {
                    thread.setUncaughtExceptionHandler(handler);
                }
                return thread;
            }
        };
    }


    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    private boolean stopTimeOutThread = false;


    public static ExecutorServiceHelper buildFixedThreadExecutor() {
        return new ExecutorServiceHelper((ThreadPoolExecutor) Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT));
    }

    /**
     * 当提及的任务超出capacity的数量时，任务会被直接丢弃并向上抛出异常
     *
     * @param name        用于确定，线程池中每一个线程的名字的前缀
     * @param threadCount 最大线程数量
     * @param handler     用于接收线程抛出的异常
     * @param capacity    线程池队列中最大的任务等待数量
     * @return
     */
    public static ExecutorServiceHelper buildFixedCountThreadExecutor(String name, int threadCount, int capacity, Thread.UncaughtExceptionHandler handler) {
        return new ExecutorServiceHelper(name, new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(capacity), new ThreadPoolExecutor.AbortPolicy()), handler);
    }

    /**
     * @param name        用于确定，线程池中每一个线程的名字的前缀
     * @param threadCount 最大线程数量
     * @param handler     用于接收线程抛出的异常
     * @return
     */
    public static ExecutorServiceHelper buildFixedThreadExecutor(String name, int threadCount, Thread.UncaughtExceptionHandler handler) {
        return new ExecutorServiceHelper(name, (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount), handler);
    }

    public static ExecutorServiceHelper buildCacheThreadExecutor() {
        return new ExecutorServiceHelper((ThreadPoolExecutor) Executors.newCachedThreadPool());
    }

    /**
     * @param name:用于确定，线程池中每一个线程的名字的前缀
     * @param handler:用于接收线程抛出的异常
     * @return
     */
    public static ExecutorServiceHelper buildCacheThreadExecutor(String name, Thread.UncaughtExceptionHandler handler) {
        return new ExecutorServiceHelper(name, (ThreadPoolExecutor) Executors.newCachedThreadPool(), handler);
    }

    public void execute(Runnable job) {
        executor.execute(job);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void execute(Runnable job, long limitTime, TimeUnit unit) {
        Future<?> future = executor.submit(job);
        timeLimitJobQueue.put(new TimeLimitFuture(future, null, limitTime, unit));
    }

    public <T> Future<T> submit(Callable<T> timeLimitJob) {
        return executor.submit(timeLimitJob);
    }

    /**
     * @param timeLimitJob
     * @param callBack             接收返回值的回调
     * @param millisecondLimitTime 毫秒值
     */
    public <T> void submit(Callable<T> timeLimitJob, CallBack<T> callBack, long millisecondLimitTime) {
        Future<T> future = executor.submit(timeLimitJob);
        timeLimitJobQueue.put(new TimeLimitFuture<T>(future, new CallBackWarp<T>(callBack), millisecondLimitTime, TimeUnit.MILLISECONDS));
    }

    public <T> void submit(Callable<T> timeLimitJob, CallBack<T> callBack, long limitTime, TimeUnit unit) {
        Future<T> future = executor.submit(timeLimitJob);
        timeLimitJobQueue.put(new TimeLimitFuture<T>(future, new CallBackWarp<T>(callBack), limitTime, unit));
    }

    public int getWattingCount() {
        return executor.getQueue().size();
    }

    public void removeAllWattingJob() {
        executor.getQueue().clear();
    }

    public void shutdownNow() {
        executor.shutdownNow();
    }

    public boolean isStopTimeOutThread() {
        return stopTimeOutThread;
    }

    public void setStopTimeOutThread(boolean stopTimeOutThread) {
        this.stopTimeOutThread = stopTimeOutThread;
    }


    public static interface CallBack<T> {
        void callback(T result);
    }

    private static class CallBackWarp<T> {
        private CallBack<T> callBack;

        public CallBackWarp(CallBack<T> callBack) {
            this.callBack = callBack;
        }

        @SuppressWarnings("unchecked")
        public void setResult(Object result) {
            callBack.callback((T) result);
        }

    }

    private static class TimeLimitFuture<T> implements Delayed {
        private Future<T> future;
        private long limitTime;
        private long triggerTime;
        private CallBackWarp<T> callbackWarp;

        public TimeLimitFuture(Future<T> future, CallBackWarp<T> callbackWarp, long limitTime, TimeUnit unit) {
            this.future = future;
            this.limitTime = limitTime;
            // 为了让时间更正确，这里进行一下时间转换
            this.triggerTime = TimeUnit.NANOSECONDS.convert(limitTime, unit) + System.nanoTime();
            this.callbackWarp = callbackWarp;
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == null || !(o instanceof TimeLimitFuture))
                return 1;
            if (o == this)
                return 0;
            TimeLimitFuture<?> future = (TimeLimitFuture<?>) o;
            if (this.limitTime > future.limitTime) {
                return 1;
            } else if (this.limitTime == future.limitTime) {
                return 0;
            } else {
                return -1;
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(triggerTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

    }
}


