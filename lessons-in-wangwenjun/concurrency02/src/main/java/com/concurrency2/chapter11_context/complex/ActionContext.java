package com.concurrency2.chapter11_context.complex;

import com.concurrency2.chapter11_context.Context;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/14 15:37
 * @version: 1.0
 ***********************/
public final class ActionContext {

    //初始化存放上下文的本地线程
    private static final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    private ActionContext() {

    }

    public void clear() {
        threadLocal.remove();
    }

    private static class ContextHolder {
        private final static ActionContext context = new ActionContext();
    }

    //获取单例
    public static ActionContext getActionContext() {
        return ContextHolder.context;
    }

    public Context getContext() {
        return threadLocal.get();
    }

}
