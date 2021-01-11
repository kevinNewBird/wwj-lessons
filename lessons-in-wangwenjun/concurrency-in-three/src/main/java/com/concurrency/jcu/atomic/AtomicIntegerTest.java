package com.concurrency.jcu.atomic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/1 15:51
 * @version: 1.0
 ***********************/
public class AtomicIntegerTest {

    //volatile关键字: 1. 可见性; 2. 避免重排序 ; 3.不能保证原子性
    //如何验证原子性的问题:开启多线程,i++(非原子性操作)作为校验的依据, 使用set进行获取数值的分析
    private static volatile int value;

    private static Set<Integer> set = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {
        //volatile原子性问题验证
//        validateVolatileAtomic();
        validateAtomicInteger();
    }

    /**
     * Description: 验证AtomicInteger <BR>
     * 优点: 1.可见性 ; 2. 有序性; 3. 原子性
     *
     * @param :
     * @return
     * @author zhao.song    2020/9/1 16:42
     */
    private static void validateAtomicInteger() throws InterruptedException {
        final AtomicInteger atomic = new AtomicInteger();
        final Thread t1 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = atomic.getAndIncrement();
                set.add(tmp);
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                x++;
            }
        });

        final Thread t2 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = atomic.getAndIncrement();
                set.add(tmp);
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                x++;
            }
        });

        final Thread t3 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = atomic.getAndIncrement();
                set.add(tmp);
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                x++;
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println("The set size:" + set.size());
    }

    /**
     * Description: 验证volatile的原子性(不保证原子性问题) <BR>
     *
     * @param :
     * @return
     * @author zhao.song    2020/9/1 16:32
     */
    private static void validateVolatileAtomic() throws InterruptedException {
        final Thread t1 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = value;
                set.add(value);
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                value += 1;
                x++;
            }
        });
        final Thread t2 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = value;
                set.add(value);
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                value += 1;
                x++;
            }
        });
        final Thread t3 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = value;
                set.add(value);
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                value += 1;
                x++;
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println("The set size:" + set.size());

    }
}
