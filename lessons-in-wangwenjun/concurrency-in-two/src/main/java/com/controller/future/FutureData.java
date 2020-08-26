package com.controller.future;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/14 10:31
 * @version: 1.0
 */
public class FutureData implements Data {
    private boolean isReady =false;

    private RealData realData;
    @Override
    public synchronized String getResult() {
        while (!isReady) {
            try{
                System.out.println("waitting....");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }

    //wait 和 notify方法必须要加锁,否则会有执行异常
    public  synchronized void setRealData(RealData realData) {
        if (isReady) {
            return ;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }
}
