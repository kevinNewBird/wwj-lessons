package com.concurrency2.chapter12_balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 16:46
 * @version: 1.0
 ***********************/
public class BalkingData {

    private final String fileName;

    private String content;

    private boolean changed;

    public BalkingData(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        changed = true;
    }

    //主动改变
    public synchronized void change(String newContent) {
        this.content = newContent;
        this.changed = true;
    }

    //被动改变
    public synchronized void save() throws IOException {
        if (!changed) {
            System.out.println("-> There is no changed,no need to save!");
            return;
        }
        doSave();
        this.changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls do save, content=" + content);
        //try...resources
        try (Writer writer = new FileWriter(fileName, true)) {
            writer.write(content);
            writer.write("\n");
            writer.flush();
        }

    }
}
