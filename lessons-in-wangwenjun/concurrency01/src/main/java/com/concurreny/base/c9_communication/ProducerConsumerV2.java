package com.concurreny.base.c9_communication;

import java.util.stream.Stream;

/**
 * @Description:线程间的通讯--【解决】多线程通信可能导致假死(reason[线程blocked放弃cpu执行权])
 * @Author:zhao.song
 * @Date:2019/12/30 18:51
 * @Version:1.0
 */
public class ProducerConsumerV2 {

    private int i = 0;

    final private Object LOCK = new Object();

    private volatile boolean isProduced = false;


    /**
     * 使用notifyAll
     */

    /**
     * Tip: 为什么线程的等待需要用while条件 , 而不能用 if ?
     * 如果使用if, 如果多个线程,当某一线程抢到produce方法的执行权,生产一条数据,并且唤醒其他consume线程且isProduced为true,该线程再次进入wait状态
     * 此时另一个produce执行线程会再次去生产一条数据,替换掉原来的数据
     */
    public void produce() {
        synchronized (LOCK) {
            /*if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println("P->" + i);
            LOCK.notifyAll();
            isProduced = true;
        }

    }

    public void consume() {
        synchronized (LOCK) {
            /*if (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C->" + i);
            LOCK.notifyAll();
            isProduced = false;


        }
    }

    public static void main(String[] args) {
        ProducerConsumerV2 pc = new ProducerConsumerV2();
        Stream.of("P1", "P2","P3").forEach(n -> {
            new Thread(n) {
                @Override
                public void run() {
                    while(true){
                        pc.produce();
                        try {
                            Thread.sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();
        });

        Stream.of("C1", "C2","C3").forEach(n -> {
            new Thread(n) {
                @Override
                public void run() {
                    while (true){
                        pc.consume();
                        try {
                            Thread.sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();
        });
    }
}
