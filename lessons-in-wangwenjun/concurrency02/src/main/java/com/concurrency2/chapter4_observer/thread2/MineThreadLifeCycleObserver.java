package com.concurrency2.chapter4_observer.thread2;

import java.util.List;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 22:34
 * @version: 1.0
 */
public class MineThreadLifeCycleObserver implements MineLifeCycleListener {
    private final Object LOCK = new Object();

    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.stream().forEach(id -> new Thread(new MineObservableRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(Thread.currentThread(), RunnableState.RUNNING, null));
                    System.out.println("query for the id " + id);
                    int i = 1 / 0;
                    notifyChange(new RunnableEvent(Thread.currentThread(), RunnableState.DONE, null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(Thread.currentThread(), RunnableState.ERROR, e));
                }
            }
        },id).start());
    }

    @Override
    public void onEvent(MineObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("The runnable [" + event.getThread().getName() +
                    "] data changed and state is [" + event.getState() + "]");
            if (event.getCause() != null) {
                System.out.println("The runnable [" + event.getThread().getName() + "] processs is failed!");
                event.getCause().printStackTrace();
            }
        }
    }
}
