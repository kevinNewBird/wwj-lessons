package com.concurrency2.chapter11_context.simple;

import com.concurrency2.chapter11_context.Context;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:21
 * @version: 1.0
 ***********************/
public class QueryFromHttpAction {

    public void execute(Context context) {
        String name = context.getName();
        String cardId = getCardId(name);
        context.setCardId(cardId);
    }

    private String getCardId(String name) {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "1121234343245435";
    }
}
