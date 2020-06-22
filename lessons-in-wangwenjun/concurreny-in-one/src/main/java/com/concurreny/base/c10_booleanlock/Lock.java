package com.concurreny.base.c10_booleanlock;

import java.util.Collection;

/**
 * Description: 自定义锁接口 <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 15:15
 * @Version: 1.0
 */
public interface Lock {

    class TimeOutException extends Exception {
        /**
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         * @param message the detail message. The detail message is saved for
         *                later retrieval by the {@link #getMessage()} method.
         */
        public TimeOutException(String message) {
            super(message);
        }
    }


    //----------定义接口start---------

    /**
     * Description: lock current thread in time <BR>
     *
     * @param :
     * @return void
     * @author zhao.song    2020/5/22 15:18
     */
    void lock() throws InterruptedException;

    /**
     * Description: try to lock current thread,if failed throw timeout <BR>
     *
     * @param mills:
     * @return void
     * @author zhao.song    2020/5/22 15:19
     */
    void lock(long mills) throws InterruptedException, TimeOutException;

    /**
     * Description: release lock <BR>
     *
     * @author zhao.song    2020/5/22 15:20
     * @param :
     * @return void
     */
    void unlock();

    /**
     * Description: get locked thread collection <BR>
     *
     * @author zhao.song    2020/5/22 15:21
     * @param :
     * @return java.util.Collection<java.lang.Thread>
     */
    Collection<Thread> getLockThreadCollection();

    /**
     * Description: get locked thread collection size<BR>
     *
     * @author zhao.song    2020/5/22 15:22
     * @param :
     * @return int
     */
    int getLockThreadCollectionSize();
    //-----------end-----------------

}
