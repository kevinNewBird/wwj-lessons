package com.concurrency2.controller.future;


/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/14 10:31
 * @version: 1.0
 */
public class Client {

    public Data request(String inData) {
        FutureData futureData = new FutureData();
        new Thread(()->{
            RealData realData = new RealData(inData);
            futureData.setRealData(realData);
        }).start();

        return futureData;
    }
}
