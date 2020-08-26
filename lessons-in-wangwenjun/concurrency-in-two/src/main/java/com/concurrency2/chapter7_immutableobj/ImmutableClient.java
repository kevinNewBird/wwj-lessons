package com.concurrency2.chapter7_immutableobj;

import java.util.stream.IntStream;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 13:57
 * @version: 1.0
 */
public class ImmutableClient {

    public static void main(String[] args) {
        //共享数据 Share Data
        Person person = new Person("kevin", "chengdu");
        IntStream.range(0,5).forEach(num->new UsePersonThread(person).start());
    }
}
