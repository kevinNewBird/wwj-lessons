package com.concurreny.base.c5_join;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 16:22
 * @Description:join()方法应用:可用于多线程读取数据
 */
public class ThreadJoinUsage {

    private static Logger logger = LoggerFactory.getLogger(ThreadJoinUsage.class);


    /**
     * Join(): 当前线程等待子线程运行结束<BR>
     * 两个名词释义:
     * 当前线程:
     * 子线程:
     */
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            logger.info("Thread t1 read data is running.");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Thread t1 read data is done!");
        });

        Thread t2 = new Thread(() -> {
            logger.info("Thread t2 read data is running.");
            try {
                Thread.sleep(15000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Thread t2 read data is done!");
        });


        Thread t3 = new Thread(() -> {
            logger.info("Thread t3 read data is running.");
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Thread t3 read data is done!");
        });


        t1.start();
        t2.start();
        t3.start();
        //当前线程(main)等待 t1/t2/t3线程执行结束后执行自己的逻辑
        //而守护线程 , 则是当前线程执行结束强行终止守护线程
        t1.join();
        t2.join();
        t3.join();
        long end = System.currentTimeMillis();
        System.out.println("==========================");
        System.out.println("Join thread spend time:" + (end - start) + "ms");

        logger.info("Save data successfully.");

        //当前线程等待当前线程结束(这个是逻辑悖论, 当前线程等待当前线程结束)
//        Thread.currentThread().join();

    }
}
