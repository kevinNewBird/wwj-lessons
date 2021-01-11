package com.classloader.chapter5;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 17:38
 * @version: 1.0
 ***********************/
public class NameSpace {

    public static void main(String[] args) throws ClassNotFoundException {
        final ClassLoader classLoader = NameSpace.class.getClassLoader();
        final Class<?> aClass = classLoader.loadClass("java.lang.String");
        final Class<?> bClass = classLoader.loadClass("java.lang.String");
        System.out.println(aClass.hashCode());
        System.out.println(aClass == bClass);

        //java.sql.Driver
    }
}
