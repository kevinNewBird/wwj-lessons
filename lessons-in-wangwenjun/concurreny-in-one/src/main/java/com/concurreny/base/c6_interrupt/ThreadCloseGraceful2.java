package com.concurreny.base.c6_interrupt;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 18:07
 * @Description:如何优雅的关闭线程--打断线程的方式
 */
public class ThreadCloseGraceful2 {

    private static class Worker extends Thread{


        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
                if (isInterrupted()){
                    break;
                }
            }
        }

    }

    public static void main(String[] args){
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //缺点: 打断的动作可能不会执行到, 打断的过程必须是wait或者join/sleep等等, 如果在此之前,线程执行任务锁住了, 无法
        worker.interrupt();

    }

}
