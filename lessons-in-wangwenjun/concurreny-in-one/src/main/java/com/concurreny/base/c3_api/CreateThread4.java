package com.concurreny.base.c3_api;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 15:50
 * @Description:
 */
public class CreateThread4 {

    private static int counter = 1;

    /**
     * Description: 压栈导致内存溢出 <BR>
     * <p>
     * stackSize这个参数不易过大 , 越大虚拟机栈的深度越小(换言之,能够创建的线程就越少)
     *
     * @param args:
     * @return void
     * @author zhao.song    2020/5/7 16:29
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);
                } catch (Error e) {
                    System.out.println(counter);
                }
            }

            private void add(int i) {
                counter++;
                add(i + 1);
            }
        }, "thread", 1 << 24);

//        System.out.println(1<<24);
        t1.start();
    }
}
