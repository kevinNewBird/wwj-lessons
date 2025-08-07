package com.classloader.chapter1;

import java.util.Random;

/***********************
 * Description: 类加载器--主动使用 <BR>
 * @author: zhao.song
 * @date: 2020/8/14 18:31
 * @version: 1.0
 ***********************/
public class ClassActiveUse {


    /*
    1. new , 直接使用
    2. 访问某个类或者接口的静态变量 , 或者对该静态变量进行赋值操作
    3. 访问静态方法
    4. 反射某个类
    5. 初始化一个子类
    6. 启动类 , 比如 : java HelloWorld
     */
    public static void main(String[] args) throws ClassNotFoundException {
        //1. new,直接使用
//        new Obj();
        //2. 访问某个类或者接口的静态变量 , 或者对该静态变量进行赋值操作
//        System.out.println(I.a);
//        System.out.println(Obj.salary);
        //3.访问静态方法
//        Obj.printSalary();
        //4.反射某个类
//        Class.forName("com.classloader.chapter1.Obj");
        //5.初始化一个子类
//        System.out.println(Child.age);
        //(1)类的被动引用,子类不会初始化(通过子类访问父类的静态变量,不会导致子类的初始化)
//        System.out.println(Child.salary);
        //(2) 类的数组引用:定义引用数组不会初始化类
        Obj[] arrays = new Obj[10];
        //(3) static会完成主动访问, 当用final修饰引用常量会不会引起类的主动使用呢?
        //final 修饰的常量会在编译期间放到常量池中,不会初始化类
        System.out.println(Obj.salary);//在编译阶段会被直接算出来,不会主动使用
        //final 修饰的负载类型,在编译期间无法计算得出,会初始化类
        System.out.println(Obj.x);//而该引用常量的值只会在运行期才会被算出来,会主动使用

    }
}

class Obj {

    public static final long salary = 100_000L;

    public static final int x = new Random().nextInt(100);

    static {
        System.out.println("Obj 被初始化.");
    }

    public static void printSalary() {
        System.out.println("=======Obj=printSalary");
    }
}

class Child extends Obj {
    public static int age = 32;

    static {
        System.out.println("Child 被初始化.");
    }
}

interface I {
    int a = 10;
}

//解读: 2. 访问某个类或者接口的静态变量 , 或者对该静态变量进行赋值操作
//对某个类的静态变量进行读写 -> 初始化class
//对接口中的静态变量进行读取 -> 初始化interface
