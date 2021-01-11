package com.classloader.chapter5;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 17:44
 * @version: 1.0
 ***********************/
public class RuntimePackage {

    //RuntimePackge
    //com.classloader.chapter5

    //运行时期的报名: Boot.Ext.App.com.classloader.chapter5
    //运行时期的报名: Boot.Ext.App.SimpleClassLoader.com.classloader.chapter5


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        final SimpleClassLoader simpleClassLoader = new SimpleClassLoader("SimpleClassloader");
        final Class<?> aClass = simpleClassLoader.loadClass("com.classloader.chapter5.SimpleObject");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());

        //运行时报名(不同的命名空间,不能相互访问)
        final SimpleObject o = (SimpleObject)aClass.newInstance();

    }
}
