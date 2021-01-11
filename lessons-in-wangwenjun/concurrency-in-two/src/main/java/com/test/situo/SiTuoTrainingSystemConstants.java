package com.test.situo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/***********************
 * Description: 宣传部培训系统工具类(对标思拓) <BR>
 * @author: zhao.song
 * @date: 2020/9/4 14:35
 * @version: 1.0
 ***********************/
public final class SiTuoTrainingSystemConstants {

    private SiTuoTrainingSystemConstants() {
        super();
    }

    //---start   思拓参数key
    public static final String APP_ID = "appid";

    public static final String SITE_ID = "siteid";

    public static final String TIME = "time";

    public static final String SIGN = "sign";

    public static final String TRS_ID = "trs_id";

    public static final String TITLE = "title";

    public static final String IMG = "img";

    public static final String SCORE = "score";

    public static final String TYPE = "type";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String CONTENT = "content";

    public static final String URI = "uri";

    public static final String FILE = "file";
    //end---

    //---start  思拓参数key对应数据库的字段名
    public static final String TRS_ID_TO_FILED_NAME = "MetaDataId";

    public static final String TITLE_TO_FILED_NAME = "Title";

    //MediaMetaDataServiceHelper.reTreatSpecialColumnToJson()
    public static final String IMG_TO_FILED_NAME = "ListPics";//(需解析ListPics字段处理)

    public static final String SCORE_TO_FILED_NAME = "Score";//暂未在app元数据添加

    public static final String TYPE_TO_FILED_NAME = "DocType";//7视频,8直播

    public static final String START_TIME_TO_FILED_NAME = "TopicStartTime";

    public static final String END_TIME_TO_FILED_NAME = "TopicEndTime";

    public static final String CONTENT_TO_FILED_NAME = "Content";

    public static final String LIVE_URI_TO_FILED_NAME = "Url";//播放地址:视频和直播需要单独处理

    //    public static final String FILE_TO_FILED_NAME = "";//暂不支持
    //end---

    //思拓直播课程类型标识
    public static final int SITUO_LIVE_TYPE = 1;

    //思拓视频课程类型标识
    public static final int SITUO_VIDEO_TYPE = 2;

    //---start 操作类型url后缀
    public static final String ADD_OPTION = "trs/add";

    public static final String UPDATE_OPTION = "trs/update";

    public static final String REMOVE_OPTION = "trs/remove";
    //end---

    public static final Map<String, String> KEY_TO_FIELD_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            put(TRS_ID, TRS_ID_TO_FILED_NAME);
            put(TITLE, TITLE_TO_FILED_NAME);
            put(IMG, IMG_TO_FILED_NAME);
            put(SCORE, SCORE_TO_FILED_NAME);
            put(TYPE, TYPE_TO_FILED_NAME);
            put(START_TIME, START_TIME_TO_FILED_NAME);
            put(END_TIME, END_TIME_TO_FILED_NAME);
            put(CONTENT, CONTENT_TO_FILED_NAME);
//            put(URI, URI_TO_FILED_NAME);
        }
    });

}
