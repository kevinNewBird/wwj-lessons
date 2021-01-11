package com.classloader.chapter4;

import org.springframework.util.StringUtils;

import java.io.*;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 15:25
 * @version: 1.0
 ***********************/
public final class EncryptUtils {

    public static final byte ENCRYPT_FACTOR = (byte) 0xff;

    private EncryptUtils() {
        //empty
        //utils工具类规范处理: 类final修饰, 且构造私有化
    }

    public static void doEncrypt(String source, String target) {
        if (StringUtils.isEmpty(source)) {
            throw new IllegalArgumentException("source is null!");
        }
        File file;
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(target)) {
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data ^ ENCRYPT_FACTOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        //加密
//        doEncrypt("E:\\classloader\\aaa.txt", "E:\\classloader\\bbb.txt");
        //解密
        doEncrypt("E:\\classloader\\bbb.txt", "E:\\classloader\\ccc.txt");
    }
}
