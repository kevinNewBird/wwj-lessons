package com.concurrency2.chapter5;

/**
 * Description: 共享资源 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 23:35
 * @version: 1.0
 */
public class Gate {

    private int counter = 0;

    private String name = "Nobody";

    private String address = "NoWhere";


    /**
     * Description: 临界值 <BR>
     * 对于门必须上锁 ,只能有一个线程通过 ,否则发生资源的竞争导致数据的不一致;
     * 这个就是贡献资源的门;
     * <p>
     * 通俗来讲,多线程访问的共享资源的方法必须上锁
     *
     * @param name:
     * @param address:
     * @return
     * @author zhao.song    2020/8/11 23:52
     */
    public synchronized void pass(String name, String address) {
        this.counter++;
        /*race*/
        this.name = name;
        this.address = address;
        verify();
    }

    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("*********BROKEN***********" + toString());
        }
    }


    public String toString() {
        return "No." + counter + ":" + name + "," + address;
    }
}
