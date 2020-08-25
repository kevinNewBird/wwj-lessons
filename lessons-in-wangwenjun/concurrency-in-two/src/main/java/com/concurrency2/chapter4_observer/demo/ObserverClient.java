package com.concurrency2.chapter4_observer.demo;

import java.util.Optional;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 18:08
 * @version: 1.0
 */
public class ObserverClient {

    public static void main(String[] args) {
        final Subject subject = new Subject();
        BinaryMineObserver binaryObserver = new BinaryMineObserver(subject);
        OctalMineObserver octalObserver = new OctalMineObserver(subject);

        Optional.of("====================").ifPresent(System.out::println);
        subject.setState(10);
        Optional.of("====================").ifPresent(System.out::println);
        subject.setState(10);
        Optional.of("====================").ifPresent(System.out::println);
        subject.setState(10);
        Optional.of("====================").ifPresent(System.out::println);
        subject.setState(1);
    }
}
