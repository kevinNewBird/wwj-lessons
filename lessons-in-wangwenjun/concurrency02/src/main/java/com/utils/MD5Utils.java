package com.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/4 10:21
 * @version: 1.0
 ***********************/
public final class MD5Utils {
    private MD5Utils() {
        super();
    }

    public static String encodeMD5(String source) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.getEncoder().encodeToString(md5.digest(source.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        final long time = new Date().getTime();
        System.out.println(time);
        System.out.println(encodeMD5("appId=ct91a542dc7df94198&siteId=1001&time=" + time)+"ct91a542dc7df94198"+"5b68d19d551f565eec5260cac64135af"+time);
//        System.out.println(encodeMD5("ct91a542dc7df94198" + "5b68d19d551f565eec5260cac64135af" + time));
    }
}
