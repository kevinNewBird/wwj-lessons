package com.classloader.chapter2;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/20 17:19
 * @version: 1.0
 ***********************/
public class BootClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        //根类加载器加载的类库, C++语法书写
        System.out.println(System.getProperty("sun.boot.class.path"));
        //扩展类加载器加载的类库,java书写
        System.out.println(System.getProperty("java.ext.dirs"));


        //系统类加载器
        Class<?> clazz = Class.forName("com.classloader.chapter2.SimpleObject");
        System.out.println(clazz.getClassLoader());
        System.out.println(clazz.getClassLoader().getParent());
        System.out.println(clazz.getClassLoader().getParent().getParent());


        //自定义同包名下的String类--父类双亲委派机制(意思就是父类先去查找类,找到就直接返回)
        Class<?> cusClazz = Class.forName("java.lang.String");
        System.out.println(cusClazz);
        System.out.println(cusClazz.getClassLoader());
    }
}
