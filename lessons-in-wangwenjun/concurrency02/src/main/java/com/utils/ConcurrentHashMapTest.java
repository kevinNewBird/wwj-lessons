package com.utils;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 9:52
 * @version: 1.0
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        System.out.println("-> HashMap test");
        testConcurrentHashMap();

        System.out.println("-> ReferenceHashMap test");
        testConcurrentReferenceHashMap();
    }


    // ConcurrentReferenceHashMap与ConcurrentHashMap的区别
    //是ConcurrentReferenceHashMap能指定所存放对象的引用级别，适用于并发下Map的数据缓存。
    //注: Java四种对象引用级别: 强引用、软引用、弱引用、虚引用
    private static void testConcurrentReferenceHashMap() {
        ConcurrentReferenceHashMap<Object, Object> tmpMap = new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
        tmpMap.put("key", "val");
        System.out.println(tmpMap);
        System.gc();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tmpMap);

    }

    private static void testConcurrentHashMap() {
        ConcurrentHashMap<Object, Object> tmpMap = new ConcurrentHashMap<>(16);
        tmpMap.put("key", "val");
        System.out.println(tmpMap);
        System.gc();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tmpMap);
    }
}
