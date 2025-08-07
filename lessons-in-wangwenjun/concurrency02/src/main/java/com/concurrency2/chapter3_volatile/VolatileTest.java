package com.concurrency2.chapter3_volatile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: volatile关键字 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 11:04
 * @version: 1.0
 */
public class VolatileTest {

    private static Logger logger = LoggerFactory.getLogger(VolatileTest.class);
    private volatile static int INIT_VALUE = 0;
    private final static int MAX_VALUE = 5;


    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_VALUE) {
                if (localValue != INIT_VALUE) {
                    logger.info("The value updated to {}", INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "READER").start();

        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_VALUE) {
                logger.info("Update the vaule to {}", ++localValue);
                INIT_VALUE = localValue;
            }

            //如果不执行休眠 ， 会导致值更新但是reader线程输出还是上一个数值
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "WRITER").start();
    }
}
