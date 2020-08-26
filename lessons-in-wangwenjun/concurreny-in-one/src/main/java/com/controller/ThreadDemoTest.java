package com.controller;

import com.controller.util.ThreadPoolExecutorServiceUtil;

import java.util.concurrent.*;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/10 10:48
 * @version: 1.0
 */
public class ThreadDemoTest {
    private static final ThreadPoolExecutor DEFAULT_EXECUTOR = new ThreadPoolExecutor(5, 10
            , 200, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(10), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }
            , new ThreadPoolExecutor.CallerRunsPolicy());

    public static void testThreadPoolV1() {
        ThreadPoolExecutor executor = ThreadPoolExecutorServiceUtil.getInstance();
        Future<?> future = null;
        try {
            future = executor.submit(() -> {
                while (true) {
                    System.out.println("->循环执行!");
                }
            });

            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            if (future != null) {
                future.cancel(true);
            }
        } finally {
            ThreadPoolExecutorServiceUtil.shutdown(10, TimeUnit.SECONDS);
            System.out.println("->关闭线程池!");
        }
    }


    public static void testThreadPoolV2() {

        Future<?> future = null;
        try {
            future = DEFAULT_EXECUTOR.submit(() -> {
                while (true) {
                    System.out.println("->循环执行!");
                }
            });

            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            if (future != null) {
                future.cancel(true);
            }
        } finally {
//            ThreadPoolExecutorServiceUtil.shutdown(10, TimeUnit.SECONDS);
            System.out.println("->关闭线程池!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long mills0=System.currentTimeMillis();
        testThreadPoolV2();
//        Thread.sleep(3_000);
        long mills1 = System.currentTimeMillis();

        System.out.println((mills1-mills0)+"线程池结束!");
    }
}
