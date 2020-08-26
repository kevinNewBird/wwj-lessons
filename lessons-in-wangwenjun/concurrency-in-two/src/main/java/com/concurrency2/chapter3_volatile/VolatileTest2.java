package com.concurrency2.chapter3_volatile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 14:39
 * @version: 1.0
 */
public class VolatileTest2 {

    private static Logger logger = LoggerFactory.getLogger(VolatileTest.class);
    private static int INIT_VALUE = 0;
    private final static int MAX_VALUE = 50;


    /**
     * 问题: 每个线程都会复制一份主内存的数据到线程的cache内存中,那么这是否以为着对于复制过去的数据
     * 所有的操作都是独立于主内存的,数值的改变不会同步到主内存?
     * 答: 否,当数值发生变化时会同步到主内存中, 但是如果不变化那么将会不通知主内存进行改变.(但是这种改变不是立即进行的)
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < MAX_VALUE) {
                logger.info("T1-> {}", ++INIT_VALUE);
                try {
                    Thread.sleep(1_00);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();

        new Thread(() -> {
            while (INIT_VALUE < MAX_VALUE) {
                logger.info("T2-> {}", ++INIT_VALUE);
                try {
                    Thread.sleep(1_00);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-2").start();
    }
}
