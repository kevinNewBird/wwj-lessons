package com.concurreny.base.c6_interrupt;

/**
 * @Author:zhao.song
 * @Date:2019/12/2 22:00
 * @Description:如何暴力的关闭线程:采用守护线程的原理
 */
public class ThreadService {
    private boolean finished = false;
    //定义执行线程 , 当执行线程结束守护线程也会结束
    private Thread executeThread;

    public void execute(Runnable task) {
        executeThread = new Thread() {

            @Override
            public void run() {
                try {
                    Thread runner = new Thread(task);
                    //执行线程结束守护线程也会结束
                    runner.setDaemon(true);
                    runner.start();

                    //join的目的:
                    // 1.是为了使暴力关闭线程时生效 , 即interrupt()
                    // 2.确保runner线程的任务执行结束
                    runner.join();//blocked
                    //为了确保任务执行时间为5秒 , 调用shutdown方法设置的时间为10秒, 在5秒执行完成后结束线程
                    finished = true;
                } catch (InterruptedException e) {

                }
            }
        };

        executeThread.start();
    }


    public void shutdown(long millis) {
        long currTime = System.currentTimeMillis();
        while (!finished) {
            if (System.currentTimeMillis() - currTime > millis) {
                System.out.println("任务超时, 执行结束任务逻辑...");
                executeThread.interrupt();
                break;
            }

            try {
                //executeThread休眠的目的: 线程的打断肯定是由别的线程调用的 , 为了确保interrupt生效,执行线程短暂的休眠一下
                executeThread.sleep(1);

            } catch (InterruptedException e) {
                System.out.println("执行线程被打断");
                break;
            }
        }
        finished = false;
    }

}
