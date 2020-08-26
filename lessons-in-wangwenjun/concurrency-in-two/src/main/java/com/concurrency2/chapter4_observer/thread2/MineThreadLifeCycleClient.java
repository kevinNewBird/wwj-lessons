package com.concurrency2.chapter4_observer.thread2;

import java.util.Arrays;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 22:40
 * @version: 1.0
 */
public class MineThreadLifeCycleClient {

    public static void main(String[] args) {
        new MineThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}
