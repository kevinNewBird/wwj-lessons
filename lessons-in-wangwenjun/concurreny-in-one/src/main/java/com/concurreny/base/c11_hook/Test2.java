package com.concurreny.base.c11_hook;

import java.util.Arrays;
import java.util.Optional;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/6/18 15:35
 * @version: 1.0
 */
public class Test2 {
    public void test() {
        Arrays.asList(Thread.currentThread().getStackTrace()).stream().filter(e->!e.isNativeMethod())
                .forEach(e-> Optional.of(e.getClassName()+":"+e.getMethodName()+":"+e.getLineNumber()).ifPresent(System.out::println));
    }
}
