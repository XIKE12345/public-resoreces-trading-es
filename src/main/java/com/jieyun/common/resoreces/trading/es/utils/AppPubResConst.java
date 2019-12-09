/**
 * 
 */
package com.jieyun.common.resoreces.trading.es.utils;

/**
 * @author ren
 * @date 2019/5/20 15:53
 */
public class AppPubResConst {

	/**模块名称作为URL前缀*/ 
    public static final String BASE_URL             = "/appPubRes";

    /**模块名称作为根路径*/
    public static final String BASE_PATH            = "appPubRes";


    /** 行政区划*/
    public static final  class AreaCodeStart {
        /**北京*/
        public static final String BJ = "11";

        /**天津*/
        public static final String TJ = "12";

        /**上海*/
        public static final String SH = "31";

        /**上海*/
        public static final String CQ = "50";

        /**央企招投标*/
        public static final String YQZTB = "67";

        /**中央政采*/
        public static final String ZYZC = "10";


    }

    /**省份结尾*/
    public static final String AREA_CODE_END = "0000";

    /** 字典类型*/
    public static final  class DictType {

        /**业务表*/
        public static final String SERVICE_TYPE = "SERVICE_TYPE";

        /**搜索表*/
        public static final String SEARCH_TYPE = "SEARCH_TYPE";

        /** 信息类型*/
        public static final String INFO_TYPE = "INFO_TYPE";

        /** 地址内容*/
        public static final String AREA_INFO = "AREA_INFO";

        /** 中标信息类型*/
        public static final String  BID_INFO_TYPE = "BID_INFO_TYPE";

        /** 招标信息类型*/
        public static final String  TENDER_INFO_TYPE = "TENDER_INFO_TYPE";

        /** ES企业信用类型*/
        public static final String  COMPANY_CREDIT_TYPE = "COMPANY_CREDIT_TYPE";

        /** 限制行为*/
        public static final String  CONFINED_ACTION = "CONFINED_ACTION";

        /** 违法违规类型*/
        public static final String  VIOLATING_TYPE = "VIOLATING_TYPE";

        /** 奖励或处罚类型*/
        public static final String  REWARD_PUNISHMENT_TYPE = "REWARD_PUNISHMENT_TYPE";

        /** 反馈问题类型 */
        public static final String QUESTION_TYPE = "QUESTION_TYPE";


    }

    /**微信OPEN ID头部获取*/
    public static final String OPEN_ID = "WechatId";

    /**微信UNION ID头部获取*/
    public static final String UNION_ID = "UnionId";

    /**微信UNION ID头部获取*/
    public static final String USER_INFO = "UserInfo";


    /**交易类型 不限*/
    public static final String INFO_TYPE_ALL = "不限";

    /**非VIP订阅最多500条*/
    public static final Integer MAX_NUM = 500;

    /** 高亮类型*/
    public static final  class HighLightType {

        /** 高亮 全文检索*/
        public static final String ALL_SEARCH = "all_search";

        /**高亮 标题检索*/
        public static final String TITLE_SEARCH = "title_search";

        /** 高亮 公告检索 */
        public static final String ANNOUNCEMENT_SEARCH = "announcement_search";

        /**高亮  企业检索 */
        public static final String COMPANY_SEARCH = "company_search";

        /** 高亮 招标单位搜索*/
        public static final String TENDER_SEARCH = "tender_search";


    }

}
