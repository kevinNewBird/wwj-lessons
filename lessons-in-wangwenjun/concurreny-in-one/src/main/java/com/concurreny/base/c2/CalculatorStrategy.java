package com.concurreny.base.c2;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 13:59
 * @Description:
 */
@FunctionalInterface
public interface CalculatorStrategy {
    public Double calculate(Double salary,Double bonus);
}
