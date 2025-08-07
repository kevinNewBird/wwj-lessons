package com.concurrency2.chapter7_immutableobj;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 13:55
 * @version: 1.0
 */
public class UsePersonThread extends Thread {

    private Person person;

    public UsePersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " print " + person);
        }
    }
}
