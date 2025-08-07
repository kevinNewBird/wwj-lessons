package com.concurrency2.chapter11_context.simple;

import com.concurrency2.chapter11_context.Context;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:15
 * @version: 1.0
 ***********************/
public class QueryFromDBAction {

    public void execute(Context context) {
        try {
            Thread.sleep(1_000);
            String name = "kEVIN - " + Thread.currentThread().getName();
            context.setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
