package com.concurrency2.chapter6_rdlock;

/**
 * Description: 共享数据 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 11:00
 * @version: 1.0
 */
public class SharedData {

    private final char[] buffer;

    private static ReadWriteLock lock = new ReadWriteLock(false);

    public SharedData(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return this.doRead();
        } finally {
            lock.readUnlock();
        }
    }

    private char[] doRead() {
        char[] newBuf = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++)
            newBuf[i] = buffer[i];

        slowly(50);
        return newBuf;
    }

    public void write(char c) throws InterruptedException {

        try {
            lock.writeLock();
            this.doWrite(c);
        } finally {
            lock.writeUnlock();
        }

    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly(10);
        }
    }

    private void slowly(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
