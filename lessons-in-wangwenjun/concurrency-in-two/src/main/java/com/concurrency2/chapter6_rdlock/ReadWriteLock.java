package com.concurrency2.chapter6_rdlock;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 10:41
 * @version: 1.0
 */
public class ReadWriteLock {

    private int readingReaders = 0;
    private int waitingReaders = 0;
    private int writingWriters = 0;
    private int waitingWriters = 0;

    private boolean prefer = true;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean prefer) {
        this.prefer = prefer;
    }

    /*
       +------+--------+---------+
              |  READ  |  WRITE  |
       ------+--------+---------+
       READ  |   N    |    Y    |
       ------+--------+---------+
       WRITE |   Y    |    Y   |
     */

    //读锁
    public synchronized void readLock() throws InterruptedException {
        //读操作,进入后必定增加等待waitingReaders的大小
        this.waitingReaders++;
        try {
            //存在线程正在写,进入等待状态
            while (this.writingWriters > 0 || (prefer && waitingWriters > 0)) {
                //wait的一个重要特性,下次获取到锁的线程会从当前位置继续执行(而非重头执行)
                this.wait();
            }
            //抢到锁
            this.readingReaders++;
        } finally {
            //抢到锁,最终必定减去当前(当前读操作已进入就加入了waiting)
            this.waitingReaders--;
        }
    }

    //释放读锁
    public synchronized void readUnlock() {
        this.readingReaders--;
        this.notifyAll();
    }

    //写锁
    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try {
            while (this.readingReaders > 0 || this.writingWriters > 0) {
                this.wait();
            }
            this.writingWriters++;
        } finally {
            this.waitingWriters--;
        }
    }

    //释放写锁
    public synchronized void writeUnlock() {
        this.writingWriters--;
        this.notifyAll();
    }
}
