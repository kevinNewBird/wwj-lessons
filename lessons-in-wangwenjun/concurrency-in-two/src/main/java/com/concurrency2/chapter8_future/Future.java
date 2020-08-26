package com.concurrency2.chapter8_future;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 15:30
 * @version: 1.0
 */
public interface Future<T> {

    T get() throws InterruptedException;
}
