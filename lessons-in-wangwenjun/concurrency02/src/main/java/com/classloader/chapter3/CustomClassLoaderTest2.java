package com.classloader.chapter3;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/20 18:14
 * @version: 1.0
 ***********************/
public class CustomClassLoaderTest2 {

    /**
     * 1. 类加载器的委托是优先交给父亲加载器先去尝试加载;
     * 2. 父加载器和子加载器其实是一种包装关系 , 或者包含关系.
     */

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //模拟父类委托机制,是一个包含关系
        CustomClassLoader myClassLoader1 = new CustomClassLoader("MyClassLoader-1");
        CustomClassLoader myClassLoader2 = new CustomClassLoader("MyClassLoader-2", myClassLoader1);
//        CustomClassLoader myClassLoader2 = new CustomClassLoader("MyClassLoader-2");

        myClassLoader2.setDir("E:\\classloader\\two");
        //分析: classLoader2 去加载的路径下为空, 所有回去找到classloader1去加载自己路径下的
        Class<?> aClass = myClassLoader2.loadClass("com.classloader.chapter3.MyObject");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());

        //静态代码块执行
        Object obj = aClass.newInstance();
    }
}
