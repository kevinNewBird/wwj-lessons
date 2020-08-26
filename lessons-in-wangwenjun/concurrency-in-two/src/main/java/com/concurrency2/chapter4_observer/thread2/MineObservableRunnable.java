package com.concurrency2.chapter4_observer.thread2;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 22:34
 * @version: 1.0
 */
public abstract class MineObservableRunnable  implements Runnable{

    private final MineLifeCycleListener listener;

    public MineObservableRunnable(MineLifeCycleListener listener) {
        this.listener = listener;
    }

    //改变数据通知
    public void notifyChange(RunnableEvent event) {
        listener.onEvent(event);
    }

    public enum RunnableState {
        RUNNING, ERROR, DONE;
    }

    public static class RunnableEvent {
        private final Thread thread;
        private final MineObservableRunnable.RunnableState state;
        private final Throwable cause;

        public Thread getThread() {
            return thread;
        }

        public RunnableState getState() {
            return state;
        }

        public Throwable getCause() {
            return cause;
        }

        public RunnableEvent(Thread thread, RunnableState state, Throwable cause) {
            this.thread = thread;
            this.state = state;
            this.cause = cause;
        }
    }
}
