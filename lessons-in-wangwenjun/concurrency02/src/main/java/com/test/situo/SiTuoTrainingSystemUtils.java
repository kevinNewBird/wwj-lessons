package com.test.situo;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/***********************
 * Description: 宣传部培训系统工具类(对标思拓) <BR>
 * @author: zhao.song
 * @date: 2020/9/4 14:35
 * @version: 1.0
 ***********************/
public final class SiTuoTrainingSystemUtils {

    private static Logger logger = LoggerFactory.getLogger(SiTuoTrainingSystemUtils.class);


    private SiTuoTrainingSystemUtils() {
        super();
    }


    //思拓鉴权参数key集合
    public static List<String> RIGHT_PARAM_LIST = Collections.unmodifiableList(new ArrayList<String>() {
        {
            add(SiTuoTrainingSystemConstants.APP_ID);
            add(SiTuoTrainingSystemConstants.SITE_ID);
            add(SiTuoTrainingSystemConstants.TIME);
        }
    });

    //思拓创建和编辑参数key集合
    private static List<String> PARAM_LIST_FOR_SAVE_OR_UPDATE = null;

    //思拓删除参数key集合
    private static List<String> PARAM_LIST_FOR_DELETE = null;

    /**
     * Description: 获取创建和保存的参数列表(不可修改且key升序) <BR>
     *
     * @param :
     * @return {@link List< String>}
     * @author zhao.song    2020/9/4 15:59
     */
    public static List<String> getSaveOrUpdateKeysList() {
        if (PARAM_LIST_FOR_SAVE_OR_UPDATE == null) {
            initSaveOrUpdateKeys();
        }
        return PARAM_LIST_FOR_SAVE_OR_UPDATE;
    }

    /**
     * Description: 获取删除的参数列表(不可修改且key升序) <BR>
     *
     * @param :
     * @return {@link List< String>}
     * @author zhao.song    2020/9/4 15:59
     */
    public static List<String> getDeleteKeysList() {
        if (PARAM_LIST_FOR_DELETE == null) {
            initDeleteKeys();
        }
        return PARAM_LIST_FOR_DELETE;
    }

    /**
     * Description: 初始化创建和编辑的参数集合 <BR>
     *
     * @param :
     * @return
     * @author zhao.song    2020/9/4 15:43
     */
    private static void initSaveOrUpdateKeys() {
        if (PARAM_LIST_FOR_SAVE_OR_UPDATE != null) {
            return;
        }
        synchronized (SiTuoTrainingSystemUtils.class) {
            if (PARAM_LIST_FOR_SAVE_OR_UPDATE != null) {
                return;
            }
            List<String> tmpList = new ArrayList<String>() {
                {
                    addAll(RIGHT_PARAM_LIST);
                    add(SiTuoTrainingSystemConstants.TRS_ID);
                    add(SiTuoTrainingSystemConstants.TITLE);
                    add(SiTuoTrainingSystemConstants.IMG);
                    add(SiTuoTrainingSystemConstants.SCORE);
                    add(SiTuoTrainingSystemConstants.TYPE);
                    add(SiTuoTrainingSystemConstants.START_TIME);
                    add(SiTuoTrainingSystemConstants.END_TIME);
                    add(SiTuoTrainingSystemConstants.CONTENT);
                    add(SiTuoTrainingSystemConstants.URI);
//                    add(SiTuoTrainingSystemConstants.FILE);
                }
            };

            PARAM_LIST_FOR_SAVE_OR_UPDATE = Collections.unmodifiableList(listSort(tmpList));
        }
    }

    /**
     * Description: 初始化删除的参数集合 <BR>
     *
     * @param :
     * @return
     * @author zhao.song    2020/9/4 15:43
     */
    private static void initDeleteKeys() {

        if (PARAM_LIST_FOR_DELETE != null) {
            return;
        }
        synchronized (SiTuoTrainingSystemUtils.class) {
            if (PARAM_LIST_FOR_DELETE != null) {
                return;
            }
            List<String> tmpList = new ArrayList<String>() {
                {
                    addAll(RIGHT_PARAM_LIST);
                    add(SiTuoTrainingSystemConstants.TRS_ID);
                }
            };
            PARAM_LIST_FOR_DELETE = Collections.unmodifiableList(listSort(tmpList));
        }
    }

    /**
     * Description: 字符串数组升序排序 <BR>
     *
     * @param input:
     * @return {@link String[]}
     * @author zhao.song    2020/9/4 14:37
     */
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

    /**
     * Description: 字符串集合升序排序 <BR>
     *
     * @param input:
     * @return {@link List< String>}
     * @author zhao.song    2020/9/4 15:53
     */
    public static List<String> listSort(List<String> input) {
        if (input == null || input.size() == 0) {
            return input;
        }
        final String[] inputArr = input.toArray(new String[input.size()]);
        final String[] outputArr = arraySort(inputArr);
        return Arrays.asList(outputArr);
    }


    public static void sendTrainingCourseToSiTuo(MarkData oMarkData, boolean isDeleteCourse) throws Exception {
        if (oMarkData == null) {
            throw new Exception("传入的课程稿件对象无效!");
        }

        // 1.判断当前稿件是否属于指定的思拓培训课程系统站点
        final String trainingSite = "1,2,3";
        if (StringUtils.isEmpty(trainingSite)) {
            logger.warn("如果需要思拓的培训系统功能,请联系管理员配置[SITUO_TRAINING_COURSE_SITE]!");
            return;
        }
        final List<Integer> trainingSiteList = Arrays.stream(trainingSite.split(","))
                .map(s -> Integer.valueOf(s))
                .distinct().collect(Collectors.toList());
        final boolean isBelongSiTuoSite = trainingSiteList.contains(Integer.parseInt(MarkData.commonDataMap.get("WebsiteId").toString()));
        // 2.获取请求url
        final String requestUrl = getRequestUrlForSiTuo(oMarkData, isBelongSiTuoSite, isDeleteCourse);
        if (StringUtils.isEmpty(requestUrl)) {
            return;
        }
        // 3.处理请求参数
        final Map<String, Object> params = handleRequestParams(oMarkData
                , trainingSiteList.contains(Integer.parseInt(MarkData.commonDataMap.get("WebsiteId").toString())), isDeleteCourse);
        if (params == null) {
            return;
        }
        // 4.发送请求
//        final String response = HttpClientHelper.doPostByFormData(requestUrl, JSON.toJSONString(params),"application/x-www-form-urlencoded",null);
//        final String response = HttpTookit.doJsonPost(requestUrl, JSONObject.fromObject(JSON.toJSONString(params)), "UTF-8", true);
//        final String response = HttpClientHelper.doPost1111(requestUrl, params);
        String response = null;
        if (isDeleteCourse) {
            response = HttpTookit.doGet(requestUrl, handleQueryStringForMap(params), "utf-8", false);
        } else {
            response = HttpTookit.doPost(requestUrl, params);
            System.out.println("请求参数: " + params);
        }
        final JSONObject resultObj = JSONObject.fromObject(response);
        if (resultObj.containsKey("state") && !resultObj.getBoolean("state")) {
            throw new Exception("请求思拓接口失败!" + (resultObj.containsKey("error")
                    ? "返回错误信息为:" + resultObj.getString("error") : ""));
        }
        System.out.println(resultObj);

    }

    private static String handleQueryStringForMap(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder queryString = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            count++;
            final String key = entry.getKey();
            final Object value = entry.getValue();
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                continue;
            }
            queryString.append(key).append("=").append(value);
            if (count < params.size()) {
                queryString.append("&");
            }
        }
        return queryString.toString();
    }

    private static String getRequestUrlForSiTuo(MarkData oMarkData
            , boolean isBelongSiTuoSite, boolean isDeleteCourse) {
        final int docType = Integer.parseInt(MarkData.commonDataMap.get("DocType").toString());
        String stTrainingUrl = "https://st-openapi.xjmty.com/v1/";
        if (isBelongSiTuoSite
                && (docType == 7 || docType == 8)) {
            if (isDeleteCourse) {
                stTrainingUrl += SiTuoTrainingSystemConstants.REMOVE_OPTION;
                return stTrainingUrl;
            }
            if (MediaConstants.contains(MediaConstants.MODAL_STATUS_YIQIANFA, Integer.parseInt(MarkData.commonDataMap.get("StatusId").toString()))) {
                stTrainingUrl += SiTuoTrainingSystemConstants.UPDATE_OPTION;
            } else {
                stTrainingUrl += SiTuoTrainingSystemConstants.ADD_OPTION;
            }
            return stTrainingUrl;
        }
        return null;
    }


    private static Map<String, Object> handleRequestParams(MarkData oMarkData
            , boolean isBelongSiTuoSite, boolean isDeleteCourse) throws Exception {
        //获取思拓课程参数(区分:删除和创建/编辑)
        final int docType = Integer.parseInt(MarkData.commonDataMap.get("DocType").toString());
        if (isBelongSiTuoSite
                && (docType == 7 || docType == 8)) {

            List<String> stParamList = null;
            if (isDeleteCourse) {
                stParamList = getDeleteKeysList();
            } else {
                stParamList = getSaveOrUpdateKeysList();
            }
            //2.遍历设置请求参数
            StringBuilder signStrBuilder = new StringBuilder();
            final HashMap<String, Object> params = new HashMap<>();
            //全局时间戳(单位:秒)
            final long oWholeTime = new Date().getTime() / 1000;
//            final long oWholeTime = new Date().getTime();
            for (int i = 0; i < stParamList.size(); i++) {
                final String sParamKey = stParamList.get(i);
                //2.1. 特殊属性的处理
                if (RIGHT_PARAM_LIST.contains(sParamKey)) {
                    handleSpecialParams(sParamKey, signStrBuilder, params, oWholeTime);
                } else {
                    //2.2. 处理稿件内容
                    //获取请求参数对应的稿件字段
                    handleMetaParams(signStrBuilder, params, oMarkData, sParamKey, docType);
                }
                if (i < stParamList.size() - 1) {
                    signStrBuilder.append("&");
                }
            }
            String signParamValue = encodeSign(signStrBuilder, oWholeTime);
            params.put(SiTuoTrainingSystemConstants.SIGN, signParamValue);
            return params;

        } else {
            logger.info(String.format("当前稿件[ID=%s]不支持入库思拓培训系统!", MarkData.commonDataMap.get("MetaDataId")));
        }
        return null;
    }


    private static String encodeSign(StringBuilder signStrBuilder, final long oWholeTime) {

        final String partSign = MD5Util.encodeMD5Hex(signStrBuilder.toString());
        System.out.println("第一次MD5前:" + signStrBuilder.toString());
        System.out.println("第一次MD5后:" + partSign);
        final String appId = "ct8b7a8f5a3d9bb62a";
        final String stSecret = "77fb3277d253e33163ee137d1acc5576";

        final String result = MD5Util.encodeMD5Hex(partSign + appId + stSecret + oWholeTime);
        System.out.println("第二次MD5前:" + partSign + appId + stSecret + oWholeTime);
        System.out.println("第二次MD5后:" + result);
        return result;
    }


    /**
     * Description: 处理稿件内容 <BR>
     *
     * @param signStrBuilder:
     * @param params:
     * @param oMarkData:
     * @param sParamKey:
     * @param docType:
     * @return
     * @author zhao.song    2020/9/7 11:45
     */
    private static void handleMetaParams(StringBuilder signStrBuilder, Map<String, Object> params
            , MarkData oMarkData, String sParamKey, int docType) throws Exception {
        //1. 处理真实的视频或直播的url
        String sRealUrlValue = null;
        if (SiTuoTrainingSystemConstants.URI.equals(sParamKey)) {
            sRealUrlValue = getRealLiveOrVideoUrl(oMarkData, docType);
            if (StringUtils.isEmpty(sRealUrlValue)) {
                throw new Exception(String.format("稿件对象[ID=%s],%s地址不可空!", MarkData.commonDataMap.get("MetaDataId")
                        , docType == 8 ? "直播" : "视频"));
            } else {
                signStrBuilder.append(sParamKey).append("=").append(URLEncoder.encode(sRealUrlValue));
                params.put(sParamKey, sRealUrlValue);
            }
        }

        final String sParamKeyToField = SiTuoTrainingSystemConstants.KEY_TO_FIELD_MAP.get(sParamKey);
        if (StringUtils.isEmpty(sParamKeyToField)) {
            return;
        }
        String tmpValueToKey = null;
        //课程封面地址处理(都是使用列表图作为课程封面)
        if (SiTuoTrainingSystemConstants.IMG_TO_FILED_NAME.equals(sParamKeyToField)) {
            tmpValueToKey = getRealLiveCoverImgUrl(null);
        } else if (SiTuoTrainingSystemConstants.TYPE.equals(sParamKey)) {
            tmpValueToKey = docType == 8
                    ? String.valueOf(SiTuoTrainingSystemConstants.SITUO_LIVE_TYPE)
                    : String.valueOf(SiTuoTrainingSystemConstants.SITUO_VIDEO_TYPE);
        } else {
            tmpValueToKey = MarkData.commonDataMap.get(sParamKeyToField).toString();
        }

        //普通属性的请求参数塞入
        if (!StringUtils.isEmpty(tmpValueToKey)) {
            signStrBuilder.append(sParamKey).append("=").append(URLEncoder.encode(tmpValueToKey));
            params.put(sParamKey, tmpValueToKey);
        }
    }

    /**
     * Description: 处理直播或视频的真实地址 <BR>
     *
     * @param oMarkData:
     * @param docType:
     * @return {@link String}
     * @author zhao.song    2020/9/7 11:12
     */
    private static String getRealLiveOrVideoUrl(MarkData oMarkData, int docType) throws Exception {
        //1. 视频
        if (docType == 7) {
            return "https://www.baidu.com/";
            //2. 直播
        } else if (docType == 8) {
            return "https://www.baidu.com/";
        }
        return null;
    }

    /**
     * Description: 处理课程封面图 <BR>
     *
     * @param sCoverImg:
     * @return {@link String}
     * @author zhao.song    2020/9/7 10:32
     */
    private static String getRealLiveCoverImgUrl(String sCoverImg) throws Exception {
        return "https://www.baidu.com";
//        return null;
    }

    /**
     * Description: 特殊属性的处理 <BR>
     *
     * @param sParamKey:
     * @param signStrBuilder:
     * @param params:
     * @return
     * @author zhao.song    2020/9/7 10:59
     */
    private static void handleSpecialParams(String sParamKey, StringBuilder signStrBuilder
            , Map<String, Object> params, final long oWholeTime) {

        switch (sParamKey) {
            case SiTuoTrainingSystemConstants.APP_ID:
                final String appId = "ct8b7a8f5a3d9bb62a";
                signStrBuilder.append(SiTuoTrainingSystemConstants.APP_ID).append("=").append(URLEncoder.encode(appId));
                params.put(SiTuoTrainingSystemConstants.APP_ID, appId);
                break;
            case SiTuoTrainingSystemConstants.SITE_ID:
                final String siteId = "10001";
                signStrBuilder.append(SiTuoTrainingSystemConstants.SITE_ID).append("=").append(URLEncoder.encode(siteId));
                params.put(SiTuoTrainingSystemConstants.SITE_ID, siteId);
                break;
            case SiTuoTrainingSystemConstants.TIME:
                signStrBuilder.append(SiTuoTrainingSystemConstants.TIME).append("=").append(oWholeTime);
                params.put(SiTuoTrainingSystemConstants.TIME, oWholeTime);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        sendTrainingCourseToSiTuo(new MarkData(), false);
//        sendTrainingCourseToSiTuo(new MarkData(), true);

    }
}
