package com.concurrency2.chapter11_context.complex;

import com.concurrency2.chapter11_context.Context;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:14
 * @version: 1.0
 ***********************/
public class ExecutionTask2 implements Runnable {


    private QueryFromDBAction2 queryAction = new QueryFromDBAction2();
    private QueryFromHttpAction2 httpAction = new QueryFromHttpAction2();

    @Override
    public void run() {
        ActionContext actionContext = ActionContext.getActionContext();
        //清除上一次线程中的结果
        actionContext.clear();
        queryAction.execute();
        System.out.println("The name query successfull.");
        httpAction.execute();
        System.out.println("The cardid query successfull.");

        Context context = actionContext.getContext();
        System.out.println("The name is " + context.getName() + " and CardId is " + context.getCardId());

    }
}
