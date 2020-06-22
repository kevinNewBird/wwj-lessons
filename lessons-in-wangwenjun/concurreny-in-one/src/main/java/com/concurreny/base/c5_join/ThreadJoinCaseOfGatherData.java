package com.concurreny.base.c5_join;

/**
 * Description: 采集数据案例 <BR>
 * 1.从多台服务器上采集数据
 * 2.采集的数据保存的采集时间应以多台服务器最后处理的结束时间为最终完成时间
 *
 * @Author: zhao.song
 * @Date: 2020/5/8 15:40
 * @Version: 1.0
 */
public class ThreadJoinCaseOfGatherData {


    public static void main(String[] args) throws InterruptedException {

        //1.数据采集
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new CaptureRunnable("M1", 10_000L));
        Thread t2 = new Thread(new CaptureRunnable("M2", 20_000L));
        Thread t3 = new Thread(new CaptureRunnable("M3", 25_000L));
        //open thread
        t1.start();
        t2.start();
        t3.start();
        //main Thread wait for sub-Thread finishing.
        t1.join();
        t2.join();
        t3.join();

        long end = System.currentTimeMillis();
        //2.保存数据
        System.out.printf("Save data bengin timestamp is: %s, end timestamp is :%s\n", start, end);

    }
}


class CaptureRunnable implements Runnable {

    private String machineName;

    private long spendTime;

    public CaptureRunnable(String machineName, long spendTime) {
        this.spendTime = spendTime;
        this.machineName = machineName;
    }

    @Override
    public void run() {
        //do the really capture data.
        try {
            Thread.sleep(spendTime);
            System.out.println(machineName + " completed data capture at timestamp and successfully.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public String getResult() {
        return machineName + " finish..";
    }
}