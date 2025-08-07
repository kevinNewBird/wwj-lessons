package com.classloader.chapter4;

import com.classloader.chapter3.CustomClassLoader;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 16:22
 * @version: 1.0
 ***********************/
public class EncryptClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.先对class进行加密
//        EncryptUtils.doEncrypt("E:\\classloader\\one\\com\\classloader\\chapter3\\MyObject.class"
//                ,"E:\\classloader\\three\\com\\classloader\\chapter3\\MyObject.class");
        //2.classloader执行时异或解密
        DecryptClassLoader myClassLoader = new DecryptClassLoader("MyDecryptClassLoader");
        Class<?> aClass = myClassLoader.loadClass("com.classloader.chapter3.MyObject");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());

        //静态代码块执行
        Object obj = aClass.newInstance();
    }
}
