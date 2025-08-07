package com.concurrency2.chapter8_future;

/**
 * Description: 同步调用 <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/12 15:18
 * @version: 1.0
 */
public class SyncInvoker {

    /*
    Future        ->  代表的是未来的一个凭据
    FutureTask    ->  将你的调用逻辑进行了隔离
    FutureService ->  桥接 Future 和 FutureTask
     */

    public static void main(String[] args) throws InterruptedException {
        /*String result = get();
        System.out.println(result);*/

        FutureService futureService = new FutureService();
        Future<String> future = futureService.submit(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        },System.out::println);

        System.out.println("-------------------");
        System.out.println("do other thing.");
        Thread.sleep(1000);
        System.out.println("==================");
        System.out.println(future.get());
    }

    private static String get() throws InterruptedException {
        Thread.sleep(10_000);
        return "FINISH";
    }
}
