package com.classloader.chapter4;

import org.springframework.util.StringUtils;

import java.io.*;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/28 16:00
 * @version: 1.0
 ***********************/
public class DecryptClassLoader extends ClassLoader {

    private final static String DEFAULT_DIR = "E:\\classloader\\three";

    private String classLoaderName;

    //自定义类加载路径
    private String cusDir = DEFAULT_DIR;

    public DecryptClassLoader(String classLoaderName) {
        this(classLoaderName, DEFAULT_DIR);
    }

    public DecryptClassLoader(ClassLoader parent, String classLoaderName, String cusDir) {
        super(parent);
        this.cusDir = cusDir;
    }

    public DecryptClassLoader(String classLoaderName, String cusDir) {
        this.classLoaderName = classLoaderName;
        this.cusDir = cusDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("the class name is null.");
        }
        String classpath = name.replace(".", "\\") + ".class";
        final File classFile = new File((cusDir), classpath);
        if (!classFile.exists()) {
            throw new ClassNotFoundException("The class " + name + " not found under directory [" + cusDir + "]");
        }
        final byte[] classBytes = loadClassBytes(new File((cusDir), classpath));

        if (classBytes == null || classBytes.length == 0) {
            throw new ClassNotFoundException("The class " + name + " load failed.");
        }
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    //字节流加密处理
    private byte[] loadClassBytes(File classFile) {
        try (FileInputStream fis = new FileInputStream(classFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int data = 0;
            while ((data = fis.read()) != -1) {
                //异或加密
                baos.write(data ^ EncryptUtils.ENCRYPT_FACTOR);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCusDir() {
        return cusDir;
    }

    public void setCusDir(String cusDir) {
        this.cusDir = cusDir;
    }
}
