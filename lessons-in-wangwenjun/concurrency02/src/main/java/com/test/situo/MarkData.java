package com.test.situo;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/7 14:04
 * @version: 1.0
 ***********************/
@Data
public class MarkData {

    private static long ONE_DAY_MILLS = 24 * 60 * 60 * 1000;

    public static final Map<String, Object> commonDataMap = new HashMap<String, Object>() {{
        put("WebsiteId", 1);
        put("MetaDataId", "125");
        put("DocType", 7);
        put("StatusId", 10);
        put("Title", "更新下下1112221");
        put("Score", 3);
        put("TopicStartTime", String.valueOf(new Date().getTime() - ONE_DAY_MILLS));
        put("TopicEndTime", String.valueOf(new Date().getTime() + ONE_DAY_MILLS));
        put("Content", "随便试试12232311");
    }};


}
