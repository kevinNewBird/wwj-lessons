package com.concurrency2.chapter9_guardedsuspension;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 18:40
 * @version: 1.0
 */
public class Request {

    private final String sendValue;

    public Request(String sendValue) {
        this.sendValue = sendValue;
    }

    public String getSendValue() {
        return sendValue;
    }
}
