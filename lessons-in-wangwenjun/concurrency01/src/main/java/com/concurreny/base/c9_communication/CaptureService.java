package com.concurreny.base.c9_communication;

import java.util.*;

/**
 * Description: 多路径下大量数据采集服务 <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 13:39
 * @Version: 1.0
 */
public class CaptureService {

    final private static LinkedList<Control> CONTROLS = new LinkedList<>();
    final static private int MAX_WORKER = 5;

    /**
     * Description: 数据存在于多服务器上的数据采集
     * 1.当采集的服务器过多,如>100台,线程问题如何处理?
     * <p>维持10个线程 , 当一个服务器的数据采集结束,让其wait</p>
     */
    public static void main(String[] args) {
        //定义worker临时存放线程容器
        List<Thread> worker = new ArrayList<>();
        //定义采集服务器
        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10").stream()
                .map(CaptureService::createThread)
                .forEach(t -> {
                            t.start();
                            worker.add(t);
                        }
                );

        //等待采集服务一起结束
        worker.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static Thread createThread(String name) {
        return new Thread(() -> {
            //并行化
            Optional.of("The [" + Thread.currentThread().getName() + "] BEGIN capture data.")
                    .ifPresent(System.out::println);
            //判断是否执行数据的采集,串行化
            synchronized (CONTROLS) {
                while (CONTROLS.size() >= MAX_WORKER) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //执行的线程未达到最大数量,放入一个CONTROL对象
                CONTROLS.addLast(new Control());
            }
            //开始执行数据的采集,并行化
            Optional.of("The [" + Thread.currentThread().getName() + "] is running ...")
                    .ifPresent(System.out::println);
            Optional.of("Current Capture Thread in running, the number is " + CONTROLS.size())
                    .ifPresent(System.out::println);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //结束采集数据,串行化
            synchronized (CONTROLS) {
                Optional.of("The [" + Thread.currentThread().getName() + "] END capture data.")
                        .ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        }, name);
    }


    //用于控制线程数
    private static class Control {

    }
}
