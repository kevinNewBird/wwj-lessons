package com.concurrency2.controller;

import com.concurrency2.controller.util.ThreadPoolExecutorServiceUtil2;
import io.vavr.control.Try;
import org.omg.CORBA.TIMEOUT;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/10 10:48
 * @version: 1.0
 */
public class ThreadDemoTest2 {
    private static final ThreadPoolExecutor DEFAULT_EXECUTOR = new ThreadPoolExecutor(5, 10
            , 200, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(10), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }
            , new ThreadPoolExecutor.AbortPolicy());

    public static void testThreadPoolV1() {
        ThreadPoolExecutor executor = ThreadPoolExecutorServiceUtil2.getInstance();
        Future<?> future = null;
        try {
            future = executor.submit(() -> {
                while (true) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "->循环执行!");
                }
            });

            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            if (future != null) {
                future.cancel(true);
            }
        } finally {
            ThreadPoolExecutorServiceUtil2.shutdown(10, TimeUnit.SECONDS);
            System.out.println("->关闭线程池!");
        }
    }


    public static void testThreadPoolV2() {

        Future<?> future = null;
        try {
            future = DEFAULT_EXECUTOR.submit(() -> {
                while (true) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "->循环执行!");
                }
            });

            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            if (future != null) {
                future.cancel(true);
            }
        } finally {
            ThreadPoolExecutorServiceUtil2.shutdown(10, TimeUnit.SECONDS);
            System.out.println("->关闭线程池!");
        }
    }


    public static void testThreadPoolV3() {

        Future<?> future = null;
        try {
            future = DEFAULT_EXECUTOR.submit(() -> {
                while (true) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "->循环执行!");
                }
            });

            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            if (future != null) {
                future.cancel(true);
            }
            if (e instanceof InterruptedException || e instanceof ExecutionException || e instanceof TimeoutException) {
                killThread((FutureTask) future);
            }
        } finally {
//            ThreadPoolExecutorServiceUtil2.shutdown(10, TimeUnit.SECONDS);
            System.out.println("->关闭线程池!");
        }
    }

    private static void killThread(FutureTask<?> submit) {
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /*long mills0 = System.currentTimeMillis();
        testThreadPoolV2();
//        Thread.sleep(3_000);
        long mills1 = System.currentTimeMillis();

        System.out.println((mills1 - mills0) + "线程池结束!");*/
        String s = Try.of(() -> getDemo(2) == null ? "maobi" : getDemo(1)).getOrElseGet(e -> "else");
        System.out.println(s);
    }


    private static String getDemo(int i) {
        if (i == 2) {
            throw new RuntimeException("lllllll");
        }
        return "ssf";
    }
}
