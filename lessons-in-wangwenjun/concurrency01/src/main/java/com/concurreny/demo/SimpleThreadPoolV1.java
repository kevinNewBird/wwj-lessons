package com.concurreny.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/19 15:12
 * @version: 1.0
 */
public class SimpleThreadPoolV1 {

    //线程池的大小
    private final int poolSize;

    final static private int DEFAULT_SIZE = 10;

    //线程池
    final private List<WorkerTaskThread> THREAD_QUEUE = new ArrayList<>();

    final static private String THREAD_PREFIX = "Thread_Pool-Task-";

    static private int seq = 0;

    //线程组
    final private ThreadGroup GROUP = new ThreadGroup("Thread_Group_V1");

    //工作队列
    static final private LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();


    public SimpleThreadPoolV1(int poolSize) {
        this.poolSize = poolSize;
        init();
    }

    public SimpleThreadPoolV1() {
        this(DEFAULT_SIZE);
    }

    //init:初始化线程池
    private void init() {
        //创建线程池
        createWorkerTaskThread();

    }

    public void createWorkerTaskThread() {
        for (int i = 0; i < poolSize; i++) {
            WorkerTaskThread thread = new WorkerTaskThread(GROUP, THREAD_PREFIX + (seq++));
            thread.start();
            THREAD_QUEUE.add(thread);
        }
    }


    //submit:任务的提交
    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            if (runnable == null) {
                throw new NullPointerException("runnable is not exists!");
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();

        }
    }


    enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD,
    }

    private static class WorkerTaskThread extends Thread {
        //线程状态:默认为空闲
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTaskThread(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while (taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    //任务队列为空时,进入等待序列
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        //关闭线程
        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }


    public static void main(String[] args) {
        SimpleThreadPoolV1 threadPool = new SimpleThreadPoolV1();
        IntStream.rangeClosed(0, 50).boxed().distinct().forEach(i -> threadPool.submit(() -> {
            System.out.println("The task " + i + " be serviced by  " + Thread.currentThread() + " start.");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The task " + i + " be serviced by  " + Thread.currentThread() + " finished.");
        }));
    }

}

