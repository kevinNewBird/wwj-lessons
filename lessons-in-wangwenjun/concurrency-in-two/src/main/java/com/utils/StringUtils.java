package com.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/4 14:00
 * @version: 1.0
 ***********************/
public final class StringUtils {

    private StringUtils() {

    }

    public static String[] arraySort(String[] input) {
        if (input == null || input.length == 0) {
            return input;
        }
        for (int i = 0; i < input.length - 1; i++) {
            for (int j = 0; j < input.length - i - 1; j++) {
                if (input[j].compareTo(input[j + 1]) > 0) {
                    String temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
        }
        return input;
    }

    public static List<String> listSort(List<String> input) {
        if (input == null || input.size() == 0) {
            return input;
        }
        final String[] inputArr = input.toArray(new String[input.size()]);
        final String[] outputArr = arraySort(inputArr);
        return Arrays.asList(outputArr);
    }


    public static void main(String[] args) {
        String[] input = new String[]{"appid", "siteid", "time", "sign", "trs_id", "title", "img", "score", "type", "start_time"
                , "end_time", "content", "uri", "file"};
        final long start = System.currentTimeMillis();
        final String[] newInput = arraySort(input);
        final long end = System.currentTimeMillis();
        System.out.println("排序耗时:" + (end - start) + " ms.");
        System.out.println(Arrays.toString(newInput));
    }


}
