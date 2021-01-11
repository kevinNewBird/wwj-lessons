package com.concurreny.demo;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.expression.ExpressionException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/7 15:12
 * @Version: 1.0
 */
public class TryFunctionProgramDemo {

    private static volatile Map<Integer, Object> LOCK_MONITOR_MAP = new HashMap<>();

    public static void main(String[] args) throws Exception {
        /*Integer orElseThrow = testTry().getOrElseThrow(()->{
            throw new RuntimeException("ssssss");});
        System.out.println(orElseThrow);*/

        /*Either orElseGet = testEither(2);
        boolean left = orElseGet.isLeft();
        Exception ex = (Exception) orElseGet.getLeft();
        System.out.println(ex.getMessage());
        Integer orElseThrow2 = testTry().getOrElseThrow((e) -> {
            throw new RuntimeException(e.getMessage());
        });
        System.out.println(orElseThrow2);*/

        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                if (i == 0) {
                    testParticlesLock0();
                } else {
                    testParticlesLock1();
                }

            }).start();
        });

        TimeUnit.SECONDS.sleep(3);

        System.out.println("-----------");


    }

    public static void testParticlesLock0() {
        synchronized (getParticlesLock(1)) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + new Date().getTime() + ":" + 1);
        }
    }

    public static void testParticlesLock1() {
        synchronized (getParticlesLock(1)) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + new Date().getTime() + ":" + 2);
        }
    }


    public static Try<Integer> testTry() throws Exception {
        return Try.of(() -> {
            return 1 / 0;
        });
    }

    public static Either<Exception, Integer> testEither(Integer num) {
        if (num == 1) {
            return Either.right(num);
        }
        return Either.left(new Exception("sssss77777s"));
    }


    //如果不加synchronized会导致锁对象产生多个
    private synchronized static Object getParticlesLock(int oLockId) {
        if (LOCK_MONITOR_MAP.get(oLockId) == null) {
            Object lock = new Object();
            LOCK_MONITOR_MAP.put(oLockId, lock);
            return lock;
        }
        return LOCK_MONITOR_MAP.get(oLockId);
    }

}
