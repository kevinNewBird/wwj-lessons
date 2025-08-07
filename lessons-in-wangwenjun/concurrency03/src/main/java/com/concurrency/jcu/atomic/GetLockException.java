package com.concurrency.jcu.atomic;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/2 15:34
 * @version: 1.0
 ***********************/
public class GetLockException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public GetLockException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public GetLockException(String message) {
        super(message);
    }
}
