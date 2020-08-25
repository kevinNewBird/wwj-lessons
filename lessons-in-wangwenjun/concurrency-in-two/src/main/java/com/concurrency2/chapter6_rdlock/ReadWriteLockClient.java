package com.concurrency2.chapter6_rdlock;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 11:14
 * @version: 1.0
 */
public class ReadWriteLockClient {

    public static void main(String[] args) {
        SharedData sharedData = new SharedData(10);
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();

        new WriterWorker(sharedData, "ST").start();
        new WriterWorker(sharedData, "AB").start();
    }
}
