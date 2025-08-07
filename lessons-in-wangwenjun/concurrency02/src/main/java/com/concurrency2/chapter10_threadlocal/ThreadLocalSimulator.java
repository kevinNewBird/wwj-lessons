package com.concurrency2.chapter10_threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/14 14:59
 * @version: 1.0
 */
public class ThreadLocalSimulator<T> {
    private final Map<Thread, T> storage = new HashMap<>();


    public void set(T t) {
        synchronized (this) {
            Thread currThread = Thread.currentThread();
            storage.put(currThread, t);
        }
    }

    public T get() {
        synchronized (this) {
            Thread currThread = Thread.currentThread();
            T value = storage.get(currThread);
            if (value == null) {
                return initialValue();
            }
            return value;
        }

    }

    protected T initialValue() {
        return null;
    }

}
