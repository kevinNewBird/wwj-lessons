package com.concurrency2.chapter9_guardedsuspension;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/14 10:05
 * @version: 1.0
 */
public class SuspensionClientTest {

    public static void main(String[] args) throws InterruptedException {
        RequestQueue requestQueue = new RequestQueue();
        ClientThread clientThread = new ClientThread(requestQueue, "I'm Kevin");
        ServerThread serverThread = new ServerThread(requestQueue);
        clientThread.start();
        serverThread.start();

        /*Thread.sleep(10_000);
        serverThread.close();*/

    }
}
