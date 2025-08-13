package com.concurreny.base.c6_interrupt;

import com.concurreny.demo.TryFunctionProgramDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 17:01
 * @Description:线程中断
 */
public class ThreadInterrupt {

    private static Logger logger = LoggerFactory.getLogger(ThreadInterrupt.class);


    /**
     * Description: 打断线程是指打断当前线程 <BR>
     *
     * @param args:
     * @return void
     * @author zhao.song    2020/5/8 17:25
     */
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    /*logger.info("Thread " + Thread.currentThread().getName() + " is running.");*/
                    Thread.sleep(1_000L);
                    logger.info("Thread " + Thread.currentThread().getName() + " is done.");
                }
            } catch (Exception e) {
                logger.info(String.format("Thread " + Thread.currentThread().getName() + "  fail.ERROR[%s]", e.getMessage()));
                e.printStackTrace();
            }
        }, "T1");

        //线程的中断 , 只有当线程在调用Object 类的 wait()、wait(long) 或 wait(long, int) 方法，
        // 或者该类的 join()、join(long)、join(long, int)、sleep(long) 或 sleep(long, int)
        //否则 , 中断interrupt的调用,当前线程仍会处于运行状态
        //@link:img/中断操作说明.png
//        t1.setDaemon(true);

        Thread t2=new Thread("T2") {
            @Override
            public void run() {
                try {
                    System.out.println(t1.isInterrupted());
                    t1.start();
                    t1.join();
                    System.out.println(Thread.currentThread().getName() + " is running...");
//                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 每个线程的打断都必须要执行：t1线程虽然在t2的run方法中，但是执行t2.interrupt方法并不能t1线程
                    // 将下面的代码注释掉将会发生t1线程一直在运行的情况
                    t1.interrupt();
                }
            }
        };
        t2.start();


        //join会阻塞
//        t1.interrupt();

        //休眠的目的是为了t1 Thread由runnable -> running
        Thread.sleep(2_000L);
        logger.info("Thread t1 is interrupted? status:" + t1.isInterrupted());
        //t1.interrupt();//打断的是t1线程的sleep方法
        t2.interrupt();
        logger.info("Thread t1 is interrupted? status:" + t1.isInterrupted());

    }
}
