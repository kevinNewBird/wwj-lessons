package com.concurrency2.chapter4_observer.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 主题 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 17:54
 * @version: 1.0
 */
public class Subject {

    private List<MineObserver> observers = new ArrayList<MineObserver>();

    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        notifyAllObserver();
    }

    public void attach(MineObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObserver() {
        observers.stream().forEach(MineObserver::update);
    }
}
