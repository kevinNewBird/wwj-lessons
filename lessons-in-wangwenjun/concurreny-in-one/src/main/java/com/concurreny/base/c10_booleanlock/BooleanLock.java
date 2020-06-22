package com.concurreny.base.c10_booleanlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Description: BooleanLock lock <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 15:23
 * @Version: 1.0
 */
public class BooleanLock implements Lock {

    //define a collection for locked thread.
    final static private Collection<Thread> oLockedThreadContainer = new ArrayList<>();

//    final static private Collection<Thread> oWhoLockedThreadContainer = new ArrayList<>();

    private Thread currThread;


    //the initvalue is true indicated get the boolean lock;
    //the initValue is false indicated the lock is free(other thread can get)
    private boolean initValue;

    public BooleanLock() {
        this.initValue = false;
    }

    /**
     * Description: lock current thread in time <BR>
     *
     * @return void
     * @author zhao.song    2020/5/22 15:18
     */
    @Override
    public synchronized void lock() throws InterruptedException {
//        Optional.of("The [" + Thread.currentThread().getName() + "] try to get lock.")
//                .ifPresent(System.out::println);

        //the lock has been gotten, sure the current thread to wait;
        while (this.initValue) {
//            Optional.of("The [" + Thread.currentThread().getName() + "] is waiting to get lock.")
//                    .ifPresent(System.out::println);
            oLockedThreadContainer.add(Thread.currentThread());
            this.wait();
        }

        Optional.of("The [" + Thread.currentThread().getName() + "] get lock monitor.")
                .ifPresent(System.out::println);
        //the current thread get the lock. set the initValue'value is true, avoid other thread to get.
        this.initValue = true;
        //make sure to who lock who release.
//        oWhoLockedThreadContainer.add(Thread.currentThread());
        currThread = Thread.currentThread();
        // remove the thread of gotten lock
        oLockedThreadContainer.remove(Thread.currentThread());
    }

    /**
     * Description: try to lock current thread,if failed throw timeout <BR>
     *
     * @param mills :
     * @return void
     * @author zhao.song    2020/5/22 15:19
     */
    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        // tolerate fault(容错机制)
        if (mills <= 0) {
            lock();
        }
        long hasRemaining = mills;
        long endTime = System.currentTimeMillis() + mills;
//        System.out.println(System.currentTimeMillis());
        while (initValue) {
//            System.out.println(hasRemaining);
            if (hasRemaining <= 0) {
                throw new TimeOutException("Time out");
            }
            oLockedThreadContainer.add(Thread.currentThread());
            this.wait(mills);
//            System.out.println("->"+System.currentTimeMillis());
            hasRemaining =endTime- System.currentTimeMillis();
            System.out.println(hasRemaining);
        }

        this.initValue = true;
        oLockedThreadContainer.remove(Thread.currentThread());
        currThread = Thread.currentThread();
    }

    /**
     * Description: release lock <BR>
     *
     * @return void
     * @author zhao.song    2020/5/22 15:20
     */
    @Override
    public synchronized void unlock() {

        //release lock, and notify locked thread
//        oLockedThreadContainer.clear();
//        if (oWhoLockedThreadContainer.contains(Thread.currentThread())) {
        if (Thread.currentThread() == currThread) {
            Optional.of("The [" + Thread.currentThread().getName() + "] release lock.")
                    .ifPresent(System.out::println);
            this.initValue = false;
            this.notifyAll();
        }

    }

    /**
     * Description: get locked thread collection <BR>
     *
     * @return java.util.Collection<java.lang.Thread>
     * @author zhao.song    2020/5/22 15:21
     */
    @Override
    public Collection<Thread> getLockThreadCollection() {
        // why unmodifiableCollection? make sure thread security.
        return Collections.unmodifiableCollection(oLockedThreadContainer);
        //The project push to a no correct project.
    }

    /**
     * Description: get locked thread collection size<BR>
     *
     * @return int
     * @author zhao.song    2020/5/22 15:22
     */
    @Override
    public int getLockThreadCollectionSize() {
        return oLockedThreadContainer.size();
    }
}
