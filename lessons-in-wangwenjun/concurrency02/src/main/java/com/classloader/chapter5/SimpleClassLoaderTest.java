package com.classloader.chapter5;

import java.util.ArrayList;
import java.util.List;

/***********************
 * Description: 打破双亲父委托机制 <BR>
 * @author: zhao.song
 * @date: 2020/8/28 16:47
 * @version: 1.0
 ***********************/
public class SimpleClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {

        List<SimpleObject> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        System.out.println(list);

        final SimpleClassLoader simpleClassLoader = new SimpleClassLoader("SimpleClassloader");
        final Class<?> aClass = simpleClassLoader.loadClass("com.classloader.chapter5.SimpleObject");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());

    }
}
