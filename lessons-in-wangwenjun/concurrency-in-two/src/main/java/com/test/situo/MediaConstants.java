/**
 *
 */
package com.test.situo;


import java.util.Optional;
import java.util.regex.Pattern;


/**
 * @author cao.hui
 *
 */
public class MediaConstants {

    public final static class Time {
        /**
         * 一分钟毫秒数
         * */
        public final static Long ONE_MINUTE_MS = 60 * 1000L;
        /**
         * 一小时毫秒数
         * */
        public final static Long ONE_HOUR_MS = 60 * ONE_MINUTE_MS;
        /**
         * 一天毫秒数（24小时）
         * */
        public final static Long ONE_DAY_MS = 24 * ONE_HOUR_MS;
        /**
         * 一个月毫秒数（30天）
         * */
        public final static Long ONE_MONTH_MS = 30 * ONE_DAY_MS;
    }


    public final static class Account {
        /**
         * 产品有效性
         */
        public enum Effectiveness {
            /**
             * 有效
             */
            EFFECTIVE(200, 0, 1),
            /**
             * 即将过期
             */
            EXPIRING_SOON(200, 1, 2),
            /**
             * 已过期
             */
            EXPIRED(200, 1, 3);

            private int code;
            private int needCall;
            /**
             * 因为code在前端具有特殊意义
             * 所以根据前端要求新增了type字段来标记有效性
             * 同时code统一置为200
             * */
            private int type;

            Effectiveness(int code, int needCall, int type) {
                this.code = code;
                this.needCall = needCall;
                this.type = type;
            }

            public int getCode() {
                return code;
            }

            public int getNeedCall() {
                return needCall;
            }

            public int getType() {
                return type;
            }

            public static Optional<Effectiveness> getEffectivenessByType(int type) {
                Effectiveness[] values = Effectiveness.values();
                for (int i = 0, len = values.length; i < len; i++) {
                    if (values[i].getType() == type) {
                        return Optional.ofNullable(values[i]);
                    }
                }
                return Optional.empty();
            }
        }
    }

    public final static Pattern MZ_REG = Pattern.compile("(meiziid)=(\"|\')(.*?)(\"|\')");

    public final static Pattern RELATION_REG = Pattern.compile("(relationid)=(\"|\')(.*?)(\"|\')");

    public final static int MZID_PRE_LENGTH = 9;

    public final static int RELATIONID_PRE_LENGTH = 12;

    public final static String MZ_OPERATION_QU = "fetch";

    public final static String MZ_OPERATION_QIAN = "publish";

    public final static String MZ_OPERATION_CHE = "cancel";

    /**
     * <p>Title:        TRS WCM</p>
     * <p>Copyright:    Copyright (c) 2004-2020</p>
     * <p>Company:      www.trs.com.cn</p>
     *
     * @author 作者: chu.chuanbao E-mail: chu.chuanbao@trs.com.cn
     * 创建时间：2020/2/5 14:39
     * @version 1.0
     * @since 1.0
     * 新媒体渠道构造相关常量值的前后缀集合
     */
    public final static class MediaConstantsPrefix {
        public final static String METAVIEW_ID_KEY = "METAVIEW_ID";
        public final static String SITEID_KEY = "SITEID";
        public final static String SITENAME_PREFIX = "产品根站点";
        public final static String METAVIEW_TEMPLATE_PATH_PREFIX = "/template";
    }

    /**
     * 抖音稿件发布成功状态
     */
    public static final int STATUS_ID_PUSHSUCCESS = 14;

    /**
     * 抖音稿件发布失败状态
     */
    public static final int STATUS_ID_PUSHFAIL = 13;

    /**
     * 实体原始ID（虚拟稿数据中塞入该数据，在通过该数据判断虚拟稿）
     * */
    public final static String ENTITY_ORIGINAL_ID = "ENTITY_ORIGINAL_ID";

    /**
     * 个人稿库
     */
    public final static String OPRTYPE_PERSONAL = "personal";
    public final static String OPRTYPE_PERSONAL_CN = "个人稿库";

    /**
     * 已收稿库
     */
    public final static String OPRTYPE_RECEIVED = "received";
    public final static String OPRTYPE_RECEIVED_CN = "已收稿库";

    /**
     * 稿件档案
     */
    public final static String OPRTYPE_DOCRECORD = "docrecord";
    public final static String OPRTYPE_DOCRECORD_CN = "稿件档案";

    /**
     * 部门稿库
     */
    public final static String OPRTYPE_DEPTMENTALDOC = "deptmentaldoc";
    public final static String OPRTYPE_DEPTMENTALDOC_CN = "部门稿库";
    /**
     * 待编
     */
    public final static String OPRTYPE_DAIBIAN = "daibian";
    public final static String OPRTYPE_DAIBIAN_CN = "待编";

    /**
     * 待审
     */
    public final static String OPRTYPE_DAISHEN = "daishen";
    public final static String OPRTYPE_DAISHEN_CN = "待审";

    /**
     * 已签发
     */
    public final static String OPRTYPE_YIQIANFA = "yiqianfa";
    public final static String OPRTYPE_YIQIANFA_CN = "已签发";

    /**
     * 定时签发
     */
    public final static String OPRTYPE_SIGNTIME = "signtime";
    public final static String OPRTYPE_SIGNTIME_CN = "定时签发";

    /**
     * 待用稿
     */
    public final static String OPRTYPE_DYG = "dyg";
    public final static String OPRTYPE_DYG_CN = "待用稿";

    /**
     * 今日稿
     */
    public final static String OPRTYPE_JRG = "jrg";
    public final static String OPRTYPE_JRG_CN = "今日稿";

    /**
     * 上版稿
     */
    public final static String OPRTYPE_SBG = "sbg";
    public final static String OPRTYPE_SBG_CN = "上版稿";

    /**
     * 已签稿
     */
    public final static String OPRTYPE_YQG = "yqg";
    public final static String OPRTYPE_YQG_CN = "已签稿";

    /**
     * 归档稿
     */
    public final static String OPRTYPE_GDG = "gdg";
    public final static String OPRTYPE_GDG_CN = "归档稿";

    /**
     * 废稿箱
     */
    public final static String OPRTYPE_RECYCLE = "recycle";
    public final static String OPRTYPE_RECYCLE_CN = "废稿箱";

    /**
     * 稿件分享管理
     * */
    public final static String OPRTYPE_DOCMANAGE = "docmanage";
    public final static String OPRTYPE_DOCMANAGE_CN = "稿件分享";

    /**
     * 敏感词审核
     * */
    public final static String OPRTYPE_ZHENGSHEN = "zhengshen";

    /**
     * 锁定/解锁稿件
     * */
    public final static String OPRTYPE_UNLOCK = "unlock";


    /**
     * 产品管理
     * */
    public final static String OPRTYPE_PRODUCT_MANAGE = "product";
    public final static String OPRTYPE_PRODUCT_MANAGE_CN = "产品管理";

    /**
     * 新建产品
     * */
    public final static String OPRNAME_PRODUCT_XINJIAN = "xinjian";

    /**
     * 编辑产品
     * */
    public final static String OPRNAME_PRODUCT_BIANJI = "bianji";

    /**
     * 删除产品
     * */
    public final static String OPRNAME_PRODUCT_DELETE = "delete";

    /**
     * 待审中退稿
     * */
    public final static String OPRNAME_TUIGAO = "tuigao";

    /**
     * 修订
     */
    public final static String OPRNAME_XIUDING = "xiuding";

    /**
     * 宣传部审核
     * */
    public final static String OPRNAME_XCBSH = "xcbsh";

    /**
     * 多元分发
     */
    public final static String OPRNAME_TRANSMIT = "transmit";

    /**
     * 锁定/解锁稿件
     * */
    public final static String OPRNAME_UNLOCK = "unlock";

    /**
     * 移动
     */
    public final static String OPRNAME_YIDONG = "yidong";

    /**
     * 送审
     */
    public final static String OPRNAME_SONGSHEN = "songshen";

    /**
     * 签发
     */
    public final static String OPRNAME_QIANFA = "qianfa";

    /**
     * 定时签发
     * */
    public final static String OPRNAME_DINGSHIQIANFA = "dingshiqianfa";

    /**
     * 取消定时签发
     */
    public final static String OPRNAME_SIGNTIMECHEXIAO = "chexiao";

    /**
     * 修改定时签发
     */
    public final static String OPRNAME_SIGNTIMEXIUGAI = "xiugai";

    /**
     * 新建
     */
    public final static String OPRNAME_XINJIAN = "xinjian";

    /**
     * 编辑
     * */
    public final static String OPRNAME_BIANJI = "bianji";

    /**
     * 废稿
     */
    public final static String OPRNAME_FEIGAO = "feigao";

    /**
     * 清空废稿箱
     */
    public final static String OPRNAME_CLEAR = "clear";

    /**
     * 编辑发稿单
     */
    public final static String OPRNAME_BIANJIFGD = "bianjiFgd";
    /**
     * 取消签发
     * */
    public final static String OPRNAME_QUXIAOQIANFA = "quxiaoqianfa";


    /**
     * 邮件外发
     */
    public final static String OPRNAME_WAIFA = "waifa";

    /**
     * 收藏
     * */
    public final static String OPRNAME_SHOUCANG = "shoucang";

    /**
     * 敏感词审核
     * */
    public final static String OPERNAME_ZHENGSHEN = "zhengshen";

    /**
     * 废稿删除
     * */
    public final static String OPRNAME_FEIGAOSHANCHU = "delete";

    /**
     * 还原废稿
     * */
    public final static String OPRNAME_FEIGAORESTORE = "restore";

    /**
     * 稿件多签
     * */
    public final static String OPRNAME_DUOQIAN = "duoqian";

    /**
     * 租户间共享
     * */
    public final static String OPRNAME_TENANTSHARE = "tenantshare";

    /**
     * 稿件置顶
     * */
    public final static String OPRNAME_ZHIDING = "zhiding";

    /**
     * 供稿/投稿  add by cheng.yin
     */
    public final static String OPRNAME_TOUGAO = "gonggao";

    /**
     * 已发稿件固定位置 add by zhao.song
     */
    public final static String OPRNAME_FIXED_POSITION = "fixedpostion";

    /**
     * 已发稿件共享稿件到已定稿库 add by recivejt
     */
    public final static String OPRNAME_YDSHARE = "ydshare";

    /**
     * 已发稿件共享稿件到已定稿库 add by recivejt
     */
    public final static String OPRNAME_MULTIVARIATEDISTRIBUTE = "transmit";

    public static final String MEDIA_TYPE_NAME_CN = "新华社";

    /**
     * 已签发稿件编辑之后送审会设置二次送审
     * */
    /**
     * 是二次送审
     * */
    public final static int SECONDREVIEW = 1;
    /**
     * 不是二次送审
     * */
    public final static int NOSECONDREVIEW = 0;

    /**
     * 取消签发时删除关联关系
     * */
    public final static int DELREL = 1;
    /**
     * 取消签发时不删除关联关系
     * */
    public final static int NODELREL = 0;


    // 待编下的操作:送审
    public final static String OPRKEY_DAIBIAN_SONGSHENG = OPRTYPE_DAIBIAN + "." + OPRNAME_SONGSHEN;

    // 待编下的操作:签发
    public final static String OPRKEY_DAIBIAN_QIANFA = OPRTYPE_DAIBIAN + "." + OPRNAME_QIANFA;


    // 已签发
    public static int[] MODAL_STATUS_YIQIANFA = new int[]{10, 12, STATUS_ID_PUSHSUCCESS, STATUS_ID_PUSHFAIL};


    public static String[] CAIBIAN_OPERTYPES = {
            OPRTYPE_DAIBIAN,
            OPRTYPE_DAISHEN, //
            OPRTYPE_YIQIANFA,
            OPRTYPE_RECYCLE,
            OPRTYPE_SIGNTIME,
            OPRTYPE_DOCMANAGE,
            // 下面是为了兼容APP和网站的回收站
            "recyle",
            "recyclemgr"
    };

    public static String[] getCaibianOprTypesOfMedia(String _sRootOprTypeOfMedia) {
        String[] pResult = new String[CAIBIAN_OPERTYPES.length];
        int i = 0;
        for (String sOprType : CAIBIAN_OPERTYPES) {
            pResult[i++] = _sRootOprTypeOfMedia + '.' + sOprType;
        }
        return pResult;
    }

    public static boolean contains(int[] _pSourceValue, int _nValue) {
        if (_pSourceValue.length == 0) {
            return true;
        }

        for (int nSourceValue : _pSourceValue) {
            if (nSourceValue == _nValue) {
                return true;
            }
        }
        return false;
    }

    public static String makeOprTypeChineseName(String _oprType) throws Exception {
        switch (_oprType) {
            case OPRTYPE_DAIBIAN:
                return OPRTYPE_DAIBIAN_CN;
            case OPRTYPE_DAISHEN:
                return OPRTYPE_DAISHEN_CN;
            case OPRTYPE_YIQIANFA:
                return OPRTYPE_YIQIANFA_CN;
            case OPRTYPE_SIGNTIME:
                return OPRTYPE_SIGNTIME_CN;
            case OPRTYPE_DYG:
                return OPRTYPE_DYG_CN;
            case OPRTYPE_JRG:
                return OPRTYPE_JRG_CN;
            case OPRTYPE_SBG:
                return OPRTYPE_SBG_CN;
            case OPRTYPE_YQG:
                return OPRTYPE_YQG_CN;
            case OPRTYPE_GDG:
                return OPRTYPE_GDG_CN;
            case OPRTYPE_RECYCLE:
                return OPRTYPE_RECYCLE_CN;
            case OPRTYPE_DOCMANAGE:
                return OPRTYPE_DOCMANAGE_CN;
            case OPRTYPE_PERSONAL:
                return OPRTYPE_PERSONAL_CN;
            case OPRTYPE_RECEIVED:
                return OPRTYPE_RECEIVED_CN;
            case OPRTYPE_DEPTMENTALDOC:
                return OPRTYPE_DEPTMENTALDOC_CN;
            case OPRTYPE_PRODUCT_MANAGE:
                return OPRTYPE_PRODUCT_MANAGE_CN;
            default:
                throw new Exception("指定的OprType[" + _oprType + "]无效!");
        }
    }


}
