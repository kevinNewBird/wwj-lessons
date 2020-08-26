package com.concurrency2.chapter11_context.simple;

import java.util.stream.IntStream;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:26
 * @version: 1.0
 ***********************/
public class ExecutionTaskClient {


    //线程上下文
    public static void main(String[] args) {
//        final ExecutionTask2 executionTask = new ExecutionTask2();
        IntStream.range(1, 5).forEach(i -> {
            new Thread(new ExecutionTask(), String.valueOf(i)).start();
        });

    }
}
