package com.concurreny.base.c7_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 16:47
 * @Description:
 */
public class SynchronizedThis {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        ThisLock thisLock = new ThisLock();
        new Thread("T1"){
            @Override
            public void run() {
                thisLock.m1();
            }
        }.start();

        new Thread("T2"){
            @Override
            public void run() {
                thisLock.m2();
            }
        }.start();


        /**
         堆栈信息如下：
         "T2" #13 prio=5 os_prio=31 tid=0x000000012c031800 nid=0x5603 waiting for monitor entry [0x000000016e1ae000]
         java.lang.Thread.State: BLOCKED (on object monitor)   -- T2线程试图获取monitor锁进入阻塞BLOCKED
         at com.concurreny.base.c7_synchronized.SynchronizedThis$ThisLock.m2(SynchronizedThis.java:43)
         - waiting to lock <0x000000076ad50628> (a com.concurreny.base.c7_synchronized.SynchronizedThis$ThisLock)
         at com.concurreny.base.c7_synchronized.SynchronizedThis$2.run(SynchronizedThis.java:24)

         "T1" #12 prio=5 os_prio=31 tid=0x000000011b009000 nid=0x7a03 waiting on condition [0x000000016dfa2000]
         java.lang.Thread.State: TIMED_WAITING (sleeping)   -- T1线程持有monitor锁，进入休眠状态TIMED_WAITING
         at java.lang.Thread.sleep(Native Method)
         at com.concurreny.base.c7_synchronized.SynchronizedThis$ThisLock.m1(SynchronizedThis.java:36)
         - locked <0x000000076ad50628> (a com.concurreny.base.c7_synchronized.SynchronizedThis$ThisLock)
         at com.concurreny.base.c7_synchronized.SynchronizedThis$1.run(SynchronizedThis.java:17)
         */
    }

    private static class ThisLock{



        public synchronized void m1(){
            System.out.println("m1 === "+ Thread.currentThread().getName());
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void m2(){
            System.out.println("m2 === "+ Thread.currentThread().getName());
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


