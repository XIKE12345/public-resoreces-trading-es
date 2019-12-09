package com.jieyun.common.resoreces.trading.es.utils;


/**
 * @author ：ren
 * @date ：Created in 2019/5/29 17:33
 */
public class EsArrayUtil {
    /**
     * 项目地址
     */
    private static String AREA_CODE = "area_code";
    /**
     * ID
     */
    private static String ID = "_id";
    /**
     * 项目所属业务类型
     */
    private static String TOS = "tos";
    /**
     * 公告标题
     */
    private static String TITLE = "title_text";
    /**
     * 发布时间
     */
    private static String CREATE_DATE = "create_date";

    /**
     * 类型
     */
    private static String TYPES = "_type";
    private static String TYPE = "type";
    /**
     * 类型
     */
    private static String INDEX = "_index";

    private static String BUDGET_PRICE = "budget_price";

    private static String BID_PRICE = "bid_price";

    private static String INFO_TYPE = "info_type";

    private static String BID_ORG = "bid_org";

    /**
     * esSearch 要查的字段
     */
    public static final String[] SEARCH_COMPARE_WORDS = {ID, TOS, CREATE_DATE, AREA_CODE, TITLE, BUDGET_PRICE, BID_PRICE,
            TYPE, TYPES,INFO_TYPE,BID_ORG, INDEX};

    /**
     * 类型
     */
    public static final String[] ES_SEARCH_TYPE = {"search_purchase"};

    /**
     * ES 基础索引
     */
    public static final String[] BASE_INDEX = {"pub_res"};


    /**
     * 项目参数
     */
    public static final class ProjectInfo {

        /**
         * 搜索字段 创建时间
         */
        public static final String CREATE_TIME = CREATE_DATE;


    }


}
