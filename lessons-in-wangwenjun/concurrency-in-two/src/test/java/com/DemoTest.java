package com;

import net.sf.json.JSONObject;
import org.junit.Test;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/3 22:05
 * @version: 1.0
 ***********************/
public class DemoTest {

    @Test
    public void testJsonObject() {
        final JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject.isEmpty());
    }
}
