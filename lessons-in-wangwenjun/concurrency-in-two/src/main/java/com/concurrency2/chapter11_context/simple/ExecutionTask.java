package com.concurrency2.chapter11_context.simple;

import com.concurrency2.chapter11_context.Context;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:14
 * @version: 1.0
 ***********************/
public class ExecutionTask implements Runnable {


    private QueryFromDBAction queryAction = new QueryFromDBAction();
    private QueryFromHttpAction httpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        final Context context = new Context();
        queryAction.execute(context);
        System.out.println("The name query successfull.");
        httpAction.execute(context);
        System.out.println("The cardid query successfull.");

        System.out.println("The name is " + context.getName() + " and CardId is " + context.getCardId());

    }
}
