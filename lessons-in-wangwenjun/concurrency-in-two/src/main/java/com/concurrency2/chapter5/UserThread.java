package com.concurrency2.chapter5;

/**
 * Description: 用户线程 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 23:40
 * @version: 1.0
 */
public class UserThread extends Thread {
    private final String myName;

    private final String myAddress;

    private final Gate gate;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public UserThread(String myName, String myAddress, Gate gate) {
        this.myName = myName;
        this.myAddress = myAddress;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(myName + " BEGIN");
        while (true) {
            this.gate.pass(myName, myAddress);
        }
    }
}
