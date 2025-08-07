package com.concurrency2.chapter11_context;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:16
 * @version: 1.0
 ***********************/
public class Context {
    private String name;
    private String cardId;



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
