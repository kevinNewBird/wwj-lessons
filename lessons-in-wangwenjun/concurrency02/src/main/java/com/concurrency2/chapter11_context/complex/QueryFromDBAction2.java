package com.concurrency2.chapter11_context.complex;

import com.concurrency2.chapter11_context.Context;

import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:15
 * @version: 1.0
 ***********************/
public class QueryFromDBAction2 {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public void execute() {
        try {
            Context context = ActionContext.getActionContext().getContext();
            Thread.sleep(1_000);
            String name = "kEVIN" + atomicInteger.getAndIncrement() + "-" + Thread.currentThread().getName();
            context.setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
