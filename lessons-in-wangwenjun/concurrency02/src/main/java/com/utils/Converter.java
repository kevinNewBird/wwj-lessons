package com.utils;

import com.exceptions.ConvertException;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 10:48
 * @version: 1.0
 */
public interface Converter<S extends Number, T> {
    T convert(S source) throws ConvertException;
}
