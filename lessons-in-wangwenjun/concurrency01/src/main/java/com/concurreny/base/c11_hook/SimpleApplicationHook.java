package com.concurreny.base.c11_hook;

/**
 * Description: 简单的钩子程序实现 <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 18:08
 * @Version: 1.0
 */
public class SimpleApplicationHook {


    public static void main(String[] args) {
        //钩子程序
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("the application run illegally,ready to quit...");
            notifyAndRelease();
        }));

        //正常的程序逻辑
        int i = 0;
        while (true) {

            try {
                System.out.println("i am working...");
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            //generate exception , try to validate whether is captured by hook.
            if (i > 10) {
                throw new RuntimeException("ERROR");
            }
        }
    }

    /**
     * Description: 通知并且释放资源<BR>
     *
     * @param :
     * @return void
     * @author zhao.song    2020/5/22 18:11
     */
    private static void notifyAndRelease() {
        System.out.println("notify to admin..");

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("will release resource(socket,file,connection.)");

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");

    }
}
