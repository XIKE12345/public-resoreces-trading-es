package com.jieyun.common.resoreces.trading.es.controller;

import com.jieyun.common.resoreces.trading.es.model.EsSearchDto;
import com.jieyun.common.resoreces.trading.es.utils.TimeScopeUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {

        String theDay  = "2018-01-01";
        String s = TimeScopeUtil.startTimeHaveT(theDay);
        System.out.println(s);
        String lastDay = LocalDate.now().toString();
        String s1 = TimeScopeUtil.endTimeHaveT(lastDay);
        System.out.println(s1);
    }



}
