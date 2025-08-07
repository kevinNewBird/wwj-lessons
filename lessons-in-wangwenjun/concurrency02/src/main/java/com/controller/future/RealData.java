package com.controller.future;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/14 10:34
 * @version: 1.0
 */
public class RealData implements Data {

    protected String result;


    public RealData(String para) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = "[" + para + "]";
    }

    @Override
    public String getResult() {
        return result;
    }
}
