package com.concurrency2.chapter7_immutableobj;

/**
 * Description: 不可变对象Person <BR>
 * <p>
 * 不可变对象定义:
 * 1. 对象中不含有setter方法
 * 2. 所有的成员变量都是final和private
 * 3. 不允许子类继承方法 , 也就是对象是final修饰的
 * 4. 一个示例引入了可变对象 , 不允许被改变
 *
 * @author: zhao.song
 * @date: 2020/8/12 13:53
 * @version: 1.0
 */
final public class Person {

    private final String name;
    private final String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
