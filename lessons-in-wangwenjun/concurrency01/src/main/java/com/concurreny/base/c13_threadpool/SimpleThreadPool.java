package com.concurreny.base.c13_threadpool;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Description: 简单的线程池实现 <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/18 18:43
 * @version: 1.0
 */
public class SimpleThreadPool extends Thread {

    //1.任务队列

    //2.拒绝策略(抛出异常,直接丢弃,阻塞,临时队列)

    //3.init(min)初始化值(初始化线程池大小)

    //4.active

    //5.max

    //min>=active>=max

    //定义线程池的大小,默认值DEFAULT_SIZE
    private int poolSize;

    //定义最大任务数(拒绝策略)
    private final int maxTaskSize;

    //线程池默认大小
    final static private int DEFAULT_SIZE = 10;

    //可执行任务默认最大值
    final static private int DEFAULT_MAX_TASK_QUEUE_SIZE = 2_000;

    //可执行任务队列
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    //定义线程名的自增后缀名
    private static volatile int seq = 0;

    private final static String THREAD_PREFIX = "SIMPLE_THREAD-POOL-";

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    //默认的拒绝异常
    private final DiscardPolicy discardPolicy;
    private final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard this task");
    };

    private volatile boolean destroy = false;

    private int min;
    private int max;
    private int active;


    public SimpleThreadPool() {
        this(DEFAULT_SIZE - 6, DEFAULT_SIZE + 2, DEFAULT_SIZE - 2, DEFAULT_MAX_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int max, int active, int maxTaskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.max = max;
        this.active = active;
        this.maxTaskSize = maxTaskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n", this.min, this.active, this.max, this.poolSize, this.TASK_QUEUE.size());
            try {
                Thread.sleep(5_000);
                if (TASK_QUEUE.size() > this.active && this.poolSize < this.active) {
                    for (int i = 0; i < this.active - this.poolSize; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to active.");
                    this.poolSize = this.active;
                } else if (TASK_QUEUE.size() > this.max && poolSize < this.max) {
                    for (int i = 0; i < this.max - this.poolSize; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to max.");
                    this.poolSize = this.max;
                }
                //占用该锁,防止别的线程进行修改(比如我在减少的过程中,存在线程疯狂的插入东西)
                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && poolSize > active) {
                        int releaseSize = poolSize - active;
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0) {
                                break;
                            }
                            WorkerTask task = it.next();
                            task.close();
                            task.interrupt();
                            it.remove();
                            releaseSize--;
                        }
                        poolSize = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Description: 初始化 <BR>
     *
     * @param :
     * @return
     * @author zhao.song    2020/6/19 14:10
     */
    private void init() {
        for (int i = 0; i < this.min; i++) {
            createWorkTask();
        }
        this.poolSize = min;
        this.start();
    }

    public void submit(Runnable runnable) {
        if (destroy) {
            throw new IllegalStateException("The thread pool alreay destroy and not allow submit task.");
        }
        synchronized (TASK_QUEUE) {
            //如果任务超过指定要求,执行拒绝策略
            if (TASK_QUEUE.size() > maxTaskSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }


    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }


    //拒绝策略异常
    public static class DiscardException extends RuntimeException {

        public DiscardException(String message) {
            super(message);
        }
    }

    //拒绝策略接口
    public interface DiscardPolicy {
        public void discard() throws DiscardException;
    }

    public void shutdown() throws InterruptedException {
        //首先判断任务队列如果不为空,等待其执行完成
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }

        synchronized (THREAD_QUEUE) {
            //如果任务队列为空,可能后面还会有任务加入进来
            int initVal = THREAD_QUEUE.size();
            //所以需要判断线程池中每个线程的状态
            while (initVal > 0) {
                for (WorkerTask workerTask : THREAD_QUEUE) {
                    //如果线程处于block状态,说明队列已经空了,执行打断,
                    // 并且让其的状态变为dead这样下次这个线程就不会在执行任务了
                    //如果线程处于非block状态,让其休眠一小会,继续执行他的任务
                    if (workerTask.getTaskState() == TaskState.BLOCKED) {
                        workerTask.interrupt();
                        workerTask.close();
                        //用于确保所有的线程池都关闭掉
                        initVal--;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
        }

//        GROUP.activeCount()
        this.destroy = true;
        System.out.println("The Thread Pool disposed.");
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getMaxTaskSize() {
        return maxTaskSize;
    }

    public boolean isDestroy() {
        return this.destroy;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    //线程状态
    enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    //自定义线程
    private static class WorkerTask extends Thread {

        //默认线程时空闲状态
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup threadGroup, String groupName) {
            super(threadGroup, groupName);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Closed.");
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.removeFirst();
                    //此处应该放到外面,否则一旦TASK_QUEUE被某一线程占有,任务的执行就是串行化
                    /*if (runnable != null) {
                        taskState = TaskState.RUNNING;
                        runnable.run();
                        taskState = TaskState.FREE;
                    }*/

                }

                //并行执行任务
                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }


        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        SimpleThreadPool pool = new SimpleThreadPool(6, 10, DEFAULT_DISCARD_POLICY);
        SimpleThreadPool pool = new SimpleThreadPool();

        IntStream.rangeClosed(0, 100).forEach(i -> pool.submit(() -> {
            System.out.println("The task " + i + " be serviced by  " + Thread.currentThread() + " start.");
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The task " + i + " be serviced by  " + Thread.currentThread() + " finished.");
        }));

        Thread.sleep(10_000);
        pool.shutdown();
//        Optional.ofNullable("Try to submit task after stop thread!").ifPresent(msg -> pool.submit(System.out::println));

    }


}
