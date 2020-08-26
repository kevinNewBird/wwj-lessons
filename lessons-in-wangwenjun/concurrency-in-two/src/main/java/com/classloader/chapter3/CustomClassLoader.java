package com.classloader.chapter3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/20 17:55
 * @version: 1.0
 **********************/

public class CustomClassLoader extends ClassLoader {

    private final static String DEFAULT_DIR = "E:\\classloader\\one";

    private String dir = DEFAULT_DIR;

    private String classLoaderName;

    public CustomClassLoader() {
        super();
    }

    public CustomClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public CustomClassLoader(String classLoaderName, ClassLoader parent) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    /*
     * Description: TODO <BR>
     * xxx.xxx.xxx.xxx.AAA
     *
     * @param name:
     * @return {@link java.lang.Class<?>}
     * @author zhao.song    2020/8/20 18:01
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "\\");
//        String classPath = name.replaceAll("\\.", "\\\\");
        File classFile = new File(dir, classPath + ".class");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("The class " + name + " not found under "
                    + classFile.getAbsolutePath());
        }
        byte[] classBytes = loadClassBytes(classFile);
        if (classBytes == null || classBytes.length == 0) {
            throw new ClassNotFoundException("The class " + name + " load failed.");
        }
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }


}
