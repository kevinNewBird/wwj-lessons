package com.concurrency.jcu.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/2 15:33
 * @version: 1.0
 ***********************/
public class CompareAndSetLock {

    private Thread lockedThread;

    private final AtomicInteger value = new AtomicInteger(0);

    public void tryLock() throws GetLockException {
        final boolean SUCCESS = value.compareAndSet(0, 1);
        if (!SUCCESS) {
            throw new GetLockException("Get the lock failed!");
        }
        lockedThread = Thread.currentThread();
    }

    public void unlock() {
        if (lockedThread != Thread.currentThread()) {
            return;
        }
        if (0 == value.get()) {
            return;
        }
        value.compareAndSet(1, 0);

    }
}
