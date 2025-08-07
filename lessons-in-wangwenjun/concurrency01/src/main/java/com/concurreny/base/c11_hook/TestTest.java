package com.concurreny.base.c11_hook;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/18 15:35
 * @version: 1.0
 */
public class TestTest {

    public static void main(String[] args) {
        //肉眼:
        //Test1调用本身的test方法,test方法又调用Test2的test方法
        new Test1().test();
    }
}
