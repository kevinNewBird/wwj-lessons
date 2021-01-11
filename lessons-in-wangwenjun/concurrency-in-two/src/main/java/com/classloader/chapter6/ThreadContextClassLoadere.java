package com.classloader.chapter6;

import com.classloader.chapter3.CustomClassLoader;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 18:42
 * @version: 1.0
 ***********************/
public class ThreadContextClassLoadere {


    public static void main(String[] args) throws ClassNotFoundException {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        //AppClassLoader
        System.out.println(contextClassLoader);

        Thread.currentThread().setContextClassLoader(new CustomClassLoader());
        final ClassLoader contextClassLoader1 = Thread.currentThread().getContextClassLoader();
        //CustomClassLoader
        System.out.println(contextClassLoader1);

        Class.forName("com.mysql.jdbc.Driver");
    }
}
