package com.concurrency2.controller.future;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/14 10:30
 * @version: 1.0
 */
public class FutureTaskDemo {

    public static void main(String[] args) {
        System.out.println(System.nanoTime());
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕 "+System.currentTimeMillis());
        //...这里做一些其它任务
        System.out.println("数据："+data.getResult());
        System.out.println("获取完毕 "+System.currentTimeMillis());
    }
}
