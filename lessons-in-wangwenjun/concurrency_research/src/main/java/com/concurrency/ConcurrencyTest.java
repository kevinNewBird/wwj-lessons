package com.concurrency;


import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 上下文切换实战
 */
public class ConcurrencyTest {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    private static final long count = 10_000L;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    private static void concurrency() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Thread thread = new Thread(() -> {
            int a = 0;
            for (long i = 0; i < count; i++) {
                a += 5;
            }
        });
        thread.start();

        int b = 0;
        for(long i = 0; i < count; i++){
            b--;
        }
        thread.join();
        stopWatch.stop();
        logger.info("concurrency spent time: {}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    private static void serial(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }

        int b = 0;
        for(long i = 0; i < count; i++){
            b--;
        }

        stopWatch.stop();
        logger.info("serial spent time: {}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
    }
}
