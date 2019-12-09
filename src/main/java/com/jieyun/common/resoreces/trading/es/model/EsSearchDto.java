package com.jieyun.common.resoreces.trading.es.model;


import lombok.Data;

@Data
public class EsSearchDto {


    /**
     * 主键
     */
    private String id;
    /**
     * 标题
     */
    private String titleText;

    /**
     * -01- 工程建设 -02- 政府采购 -03- 土地 -04- 国有 -90- 是其他
     * 招标类型
     */
    private int tos;

    /**
     * 招标时间
     */
    private String createDate;

    /**
     * 省编码
     * -10-开头的为中央，不用操作
     */
    private String areaCode;

    private String index;

    private String type;
    private String types;

    private String budgetPrice;
    private String bidPrice;
    private String infoType;
    private String bidOrg;





}
