package com.jieyun.common.resoreces.trading.es.model;


import lombok.Data;

@Data
public class Pager {

    /**
     * 起始行
     */
    private int startRow ;

    /**
     * 每次条数
     */
    private int totalSize;
}
