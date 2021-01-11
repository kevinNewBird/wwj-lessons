package com.personal;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/27 9:40
 * @version: 1.0
 ***********************/
public class StaticVariableTest {

    /**
     * 大数据默认添加的防盗链域名
     */
    public static final String SAFETY_CHAIN = "https://images.weserv.nl/?url=";

    public static final String SAFETY_CHAIN1 = "https://images.weserv.nl?url=";

    //默认需做预处理替换的地址前缀
    public static JSONArray preDealDefaultUrl = null;

    private static void initDefaultUrl() {
        if (preDealDefaultUrl != null) {
            return;
        }
        synchronized (StaticVariableTest.class) {
            if (preDealDefaultUrl != null) {
                return;
            }
            JSONArray tmpArr = new JSONArray();
            tmpArr.add(SAFETY_CHAIN);
            tmpArr.add(SAFETY_CHAIN1);
            preDealDefaultUrl = tmpArr;
        }
    }

    public static JSONArray getPreDealDefaultUrl() {
        if (preDealDefaultUrl == null) {
            initDefaultUrl();
        }
        return preDealDefaultUrl;
    }

    public static void main(String[] args) {
        ArrayList<String> resortOrderInfoList = new ArrayList<>();
        resortOrderInfoList.add("a");
        resortOrderInfoList.add("b");
        resortOrderInfoList.add("c");
        System.out.println(resortOrderInfoList.size());
        int currIndex = Math.min(3,5);
        resortOrderInfoList.add(currIndex, "d");
        System.out.println(resortOrderInfoList);
    }




}
