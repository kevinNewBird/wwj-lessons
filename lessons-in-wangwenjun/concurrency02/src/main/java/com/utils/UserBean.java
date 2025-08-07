package com.utils;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/7 17:23
 * @version: 1.0
 */
public class UserBean {
    private int id;
    private String name;
    private Integer age;

    public UserBean() {
    }

    public UserBean(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
