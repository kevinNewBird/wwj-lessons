package com.concurrency2.chapter9_guardedsuspension;

import java.util.LinkedList;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 18:41
 * @version: 1.0
 */
public class RequestQueue {

    private static LinkedList<Request> queue = new LinkedList<>();


    public Request getRequest() {
        synchronized (queue) {
            while (queue.size() <= 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return queue.removeFirst();
        }
    }

    public void setRequest(Request request) {
        synchronized (queue) {
            queue.addLast(request);
            queue.notifyAll();
        }
    }

    public static void main(String[] args) {
        //获取不到元素会抛出异常 NoSuchElementException
        Request request = queue.removeFirst();
    }

}
