package com.concurrency2.chapter12_balking;

import java.io.IOException;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 16:56
 * @version: 1.0
 ***********************/
public class BalkingDataTest {

    public static void main(String[] args) {
        BalkingData data = new BalkingData("balking.txt", "fsafsdf");
        new Thread(()->{
            try {
                data.change("sss");
                data.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                data.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"T2").start();
    }
}
