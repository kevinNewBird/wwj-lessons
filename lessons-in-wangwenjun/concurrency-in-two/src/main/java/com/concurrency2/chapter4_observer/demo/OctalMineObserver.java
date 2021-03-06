package com.concurrency2.chapter4_observer.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 18:04
 * @version: 1.0
 */
public class OctalMineObserver extends MineObserver {

    private static Logger logger = LoggerFactory.getLogger(OctalMineObserver.class);

    public OctalMineObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Octal String: " + Integer.toOctalString(subject.getState()));
    }
}
