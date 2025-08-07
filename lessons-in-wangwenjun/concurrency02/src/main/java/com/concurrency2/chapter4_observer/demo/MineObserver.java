package com.concurrency2.chapter4_observer.demo;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 17:57
 * @version: 1.0
 */
public abstract class MineObserver {
    protected Subject subject;

    public MineObserver(Subject subject) {
        this.subject = subject;
        //设置主题绑定该观察器
        this.subject.attach(this);
    }

    public abstract void update();
}
