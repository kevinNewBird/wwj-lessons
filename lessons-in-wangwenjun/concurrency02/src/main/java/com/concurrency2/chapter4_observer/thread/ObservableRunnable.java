package com.concurrency2.chapter4_observer.thread;

/**
 * Description: 定义可观察的线程对象 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 21:13
 * @version: 1.0
 */
public abstract class ObservableRunnable implements Runnable {

    final protected LifeCycleListener listener;

    public ObservableRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    protected void notifyChange(RunnableEvent event) {
        listener.onEvent(event);
    }

    public enum RunnableState{
        RUNNING,ERROR,DONE;
    }
    public static class RunnableEvent{
        private final RunnableState state;
        private final Thread thread;
        private final Throwable cause;

        public RunnableState getState() {
            return state;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getCause() {
            return cause;
        }

        public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
            this.state = state;
            this.thread = thread;
            this.cause = cause;
        }
    }
}
