package com.exceptions;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 10:57
 * @version: 1.0
 */
public class ConvertException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ConvertException() {
        super();
    }


    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ConvertException(String message) {
        super(message);
    }

    public static void main(String[] args) {
        String s = "";
        s = "ssss";
        System.out.println(s);
    }
}
