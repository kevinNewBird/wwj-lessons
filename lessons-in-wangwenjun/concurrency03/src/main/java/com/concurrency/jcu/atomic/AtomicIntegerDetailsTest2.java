package com.concurrency.jcu.atomic;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/1 17:14
 * @version: 1.0
 ***********************/
public class AtomicIntegerDetailsTest2 {


    //unsafe是不建议使用的 , 在jdk1.9是被拿掉了的

    public static void main(String[] args) throws NoSuchFieldException {
        final CompareAndSetLock tryLock = new CompareAndSetLock();
        IntStream.range(0, 5).forEach((i)->{
            new Thread(()->{
                try {
                    tryLock.tryLock();
                    Optional.of(Thread.currentThread().getName() + " is running.").ifPresent(System.out::println);
                    Thread.sleep(1_000);
                } catch (GetLockException | InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    tryLock.unlock();
                }
            }).start();
        });
    }
}
