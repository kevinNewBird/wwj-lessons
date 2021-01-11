package com.classloader.chapter4;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 15:08
 * @version: 1.0
 ***********************/
public class SimpleEncrypt {

    private static final String plain = "hello classloader";

    private static final byte ENCRYPT_FACTOR = (byte) 0xff;

    public static void main(String[] args) {
        //默认的编码格式(和操作系统有关)
        byte[] sBytes = plain.getBytes();

        byte[] encrypt = new byte[sBytes.length];

        for (int i = 0; i < sBytes.length; i++) {
            //异或
            encrypt[i] = (byte) (sBytes[i] ^ ENCRYPT_FACTOR);
        }
        //打印密文
        System.out.println(new String(encrypt));

        //解密
        byte[] decrypt = new byte[encrypt.length];

        for (int i = 0; i < encrypt.length; i++) {
            decrypt[i] = (byte) (encrypt[i] ^ ENCRYPT_FACTOR);
        }

        System.out.println(new String(decrypt));
    }
}
