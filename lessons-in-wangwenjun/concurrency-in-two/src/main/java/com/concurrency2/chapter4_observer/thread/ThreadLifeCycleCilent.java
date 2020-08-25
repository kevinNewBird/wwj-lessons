package com.concurrency2.chapter4_observer.thread;

import java.util.Arrays;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 21:29
 * @version: 1.0
 */
public class ThreadLifeCycleCilent {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("111"));
    }
}
