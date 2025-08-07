package com.concurrency2.chapter9_guardedsuspension;

import java.util.Random;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/14 9:54
 * @version: 1.0
 */
public class ClientThread extends Thread {

    private final RequestQueue queue;

    private final String sendValue;

    private final Random random;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        this.sendValue = sendValue;
        this.random = new Random(System.currentTimeMillis());
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Client -> request " + sendValue);
            queue.setRequest(new Request(sendValue));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
