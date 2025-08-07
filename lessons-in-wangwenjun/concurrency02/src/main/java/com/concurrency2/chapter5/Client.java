package com.concurrency2.chapter5;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 23:42
 * @version: 1.0
 */
public class Client {

    public static void main(String[] args) {
        //共享资源
        Gate gate = new Gate();

        UserThread bj = new UserThread("Baobao", "Beijing", gate);
        UserThread sh = new UserThread("ShangLao", "Shanghai", gate);
        UserThread gz = new UserThread("GuangLao", "Guangzhou", gate);

        bj.start();
        sh.start();
        gz.start();
    }
}
