package com.concurrency2.chapter9_guardedsuspension;

import java.util.Random;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/14 9:59
 * @version: 1.0
 */
public class ServerThread extends Thread {

    private final RequestQueue queue;

    private final Random random;
    private volatile boolean closed = false;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!closed) {
            Request request = queue.getRequest();
            if (request == null) {
                System.out.println("Received the empty request.");
                continue;
            }
            System.out.println("Server -> " + request.getSendValue());
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void close() {
        this.closed = true;
        this.interrupt();
    }
}
