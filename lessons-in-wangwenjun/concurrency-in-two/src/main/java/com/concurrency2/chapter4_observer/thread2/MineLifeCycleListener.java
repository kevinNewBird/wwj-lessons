package com.concurrency2.chapter4_observer.thread2;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 22:28
 * @version: 1.0
 */
public interface MineLifeCycleListener {

    void onEvent(MineObservableRunnable.RunnableEvent event);
}
