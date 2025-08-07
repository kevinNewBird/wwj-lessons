package com.concurreny.base.c10_booleanlock;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 15:37
 * @Version: 1.0
 */
public class BooleanLockTest {


    public static void main(String[] args) throws InterruptedException {
        //init lock
        Lock lock = new BooleanLock();
        //use lock
//        Thread t1 = new Thread(() -> {
        Stream.of("T1", "T2", "T3", "T4", "T5").forEach(tName -> {
            new Thread(() -> {
                try {
//                    lock.lock();
                    lock.lock(100L);

                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Lock.TimeOutException e) {
                    Optional.of(Thread.currentThread().getName() + " time out").ifPresent(System.out::println);
                } finally {
                    lock.unlock();

                }
            }, tName).start();
        });
//        });
//        t1.start();

        Thread.sleep(200);
        //bug: other thread can release lock so that waited lock get lock
        //tip: who lock who release.(谁加的锁谁释放)
        lock.unlock();


    }

    private static void work() {
        Optional.of("The [" + Thread.currentThread().getName() + "] is Working.").ifPresent(System.out::println);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
