package com.concurreny.base.c8_deadlock;

import java.util.concurrent.CountDownLatch;

public class DeadLockTest2 {

    private final Object MUTEX_READ = new Object();
    private final Object MUTEX_WRITE = new Object();
    private final CountDownLatch latch = new CountDownLatch(2);

    public void read() {
        latch.countDown();
        synchronized (MUTEX_READ) {
            System.out.println(Thread.currentThread().getName() + " get Read lock");
            synchronized (MUTEX_WRITE) {
                System.out.println(Thread.currentThread().getName() + " get Write lock");
            }
            System.out.println(Thread.currentThread().getName() + " release Write lock");
        }
        System.out.println(Thread.currentThread().getName() + " release Read lock");
    }

    public void write() {
        latch.countDown();
        synchronized (MUTEX_WRITE) {
            System.out.println(Thread.currentThread().getName() + "get Write lock");
            synchronized (MUTEX_READ) {
                System.out.println(Thread.currentThread().getName() + " get Read lock");
            }
            System.out.println(Thread.currentThread().getName() + " release Read lock");
        }
        System.out.println(Thread.currentThread().getName() + " release Write lock");
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockTest2 deadLockTest2 = new DeadLockTest2();
        new Thread(() -> {
            while (true) {
                deadLockTest2.read();
            }
        }, "READ-THREAD").start();
        new Thread(() -> {
            while (true) {
                deadLockTest2.write();
            }
        }, "WRITE-THREAD").start();
        deadLockTest2.latch.await();

        /**
         堆栈信息如下：
         "WRITE-THREAD" #13 prio=5 os_prio=31 tid=0x000000014e8a5800 nid=0x7b03 waiting for monitor entry [0x0000000172bba000]
         java.lang.Thread.State: BLOCKED (on object monitor)
         at com.concurreny.base.c8_deadlock.DeadLockTest2.write(DeadLockTest2.java:28)
         - waiting to lock <0x000000076ad49388> (a java.lang.Object) -- READ-THREAD线程持有0x000000076ad49388
         - locked <0x000000076ad49398> (a java.lang.Object)          -- 当前线程持有的锁
         at com.concurreny.base.c8_deadlock.DeadLockTest2.lambda$main$1(DeadLockTest2.java:44)
         at com.concurreny.base.c8_deadlock.DeadLockTest2$$Lambda$2/1560911714.run(Unknown Source)
         at java.lang.Thread.run(Thread.java:750)

         "READ-THREAD" #12 prio=5 os_prio=31 tid=0x000000014e8a4800 nid=0x7c03 waiting for monitor entry [0x00000001729ae000]
         java.lang.Thread.State: BLOCKED (on object monitor)
         at com.concurreny.base.c8_deadlock.DeadLockTest2.read(DeadLockTest2.java:16)
         - waiting to lock <0x000000076ad49398> (a java.lang.Object) -- WRITE-THREAD线程持有0x000000076ad49398
         - locked <0x000000076ad49388> (a java.lang.Object)          -- 当前线程持有的锁
         at com.concurreny.base.c8_deadlock.DeadLockTest2.lambda$main$0(DeadLockTest2.java:39)
         at com.concurreny.base.c8_deadlock.DeadLockTest2$$Lambda$1/396180261.run(Unknown Source)
         at java.lang.Thread.run(Thread.java:750)
         */
    }
}
