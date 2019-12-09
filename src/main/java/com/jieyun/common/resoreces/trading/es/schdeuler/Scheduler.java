package com.jieyun.common.resoreces.trading.es.schdeuler;

import com.jieyun.common.resoreces.trading.es.model.EsSearchDto;
import com.jieyun.common.resoreces.trading.es.model.Pager;
import com.jieyun.common.resoreces.trading.es.service.impl.EsServiceImpl;
import com.jieyun.common.resoreces.trading.es.utils.EsArrayUtil;
import com.jieyun.common.resoreces.trading.es.utils.JsonUtil;
import com.jieyun.common.resoreces.trading.es.utils.TimeScopeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class Scheduler {


    private final RestHighLevelClient restHighLevelClient;

    private final EsServiceImpl esService;

    private Map<String, List<String>> globleMap = new HashMap<>();

    @Value("${es.startSearchTime}")
    public String searchTime;


    @Autowired
    public Scheduler(EsServiceImpl esService,
                              @Qualifier("restHighLevelClient") RestHighLevelClient restHighLevelClient) {
        this.esService = esService;
        this.restHighLevelClient = restHighLevelClient;
    }

        @Scheduled(fixedRate = 1000 * 5 * 30)
//    @GetMapping("/get/all")
    public void init() {
        try {


            //获取并处理查询到的数据
            getInitAll(searchTime);

            // 删除重复数据
            deleteRepeatDate();

            //清除Map中的数据
            globleMap.clear();
        } catch (Exception e) {
            log.error("init method error", e);
        }
    }

    public void getInitAll(String time) {
        long l1 = System.currentTimeMillis();
        Pager pager = new Pager();
        //获取查询数据(初始化查询总条数)
        SearchResponse searchResponse = getEsDatas(pager, time);
        int maxStart = 0;
        String createTime = null;

        int j = 1000;
        int k = 9000;
        log.info("searchResponse.getHits().getTotalHits() is ---{}", searchResponse.getHits().getTotalHits());
        if (searchResponse.getHits().getTotalHits() > 9000) {
            long totalHits = searchResponse.getHits().getTotalHits();
            log.info("search end after totals num is {}", totalHits);
            for (int i = 0; i < searchResponse.getHits().getTotalHits(); i += 1000) {
                pager.setStartRow(i);
                pager.setTotalSize(j);
                SearchResponse search = getEsDatas(pager, time);
                int length = search.getHits().getHits().length;
                log.info("search.getHits().getHits().length is {}", length);
                //处理Es中的数据
                handleEsDatas(search);
                maxStart = i;
                log.info("------------- i = {}", i);
                if (i >= 9000) {
                    EsSearchDto esSearchDto = JsonUtil.parseObject(search.getHits().getHits()[length - 1].getSourceAsString(), EsSearchDto.class);
                    log.info("---------- is {}", search.getHits().getHits()[length - 1].getSourceAsString());
                    createTime = esSearchDto.getCreateDate();
                    break;
                }
            }
            if (maxStart >= 9000) {
                log.info("createTime = {}", createTime);
                getInitAll(createTime);
            }
        } else {
            long l2 = System.currentTimeMillis();
            log.info("--------查询时间为： {}", l2 - l1 + "ms");
            log.info("-------总条数为:{}", searchResponse.getHits().getTotalHits());

            log.info("总条数小于9000");
            for (int i = 0; i < searchResponse.getHits().getTotalHits(); i += j) {
                pager.setStartRow(i);
                pager.setTotalSize(j);
                SearchResponse search = getEsDatas(pager, time);
                long l4 = System.currentTimeMillis();
                //处理Es中的数据
                log.info("查询消耗时间 ： {}", l4 - l2 + " ms");
                long totalHits = search.getHits().getTotalHits();
                log.info("--------totalHits------", totalHits);
                handleEsDatas(search);
            }
            long l3 = System.currentTimeMillis();
            log.info("查询完消耗的时间为：{}", l3 - l2 + " ms");
        }
    }

    /**
     * 获取查询数据
     *
     * @param pager
     * @param startTime
     * @return
     */
    public SearchResponse getEsDatas(Pager pager, String startTime) {
        String theDay;
        if (StringUtils.isNotBlank(startTime)) {
            theDay = startTime;
        } else {
            theDay = LocalDateTime.now().minusHours(-8).toString();
        }
        String lastDay = LocalDate.now().toString();
        SearchResponse searchResponse = new SearchResponse();
        try {
            SearchRequest searchRequest = esService.getSearchRequest(
                    EsArrayUtil.BASE_INDEX,
                    EsArrayUtil.ES_SEARCH_TYPE,
                    EsArrayUtil.SEARCH_COMPARE_WORDS,
                    EsArrayUtil.ProjectInfo.CREATE_TIME,
                    SortOrder.ASC,
                    pager,
                    null,
                    null,
                    QueryBuilders.rangeQuery(EsArrayUtil.ProjectInfo.CREATE_TIME)
                            .gte(theDay)
                            .lte(TimeScopeUtil.endTimeHaveT(lastDay))
            );
            //查询Es
            searchResponse = restHighLevelClient.search(searchRequest);
            log.info("searchResponse total is {}", searchResponse.getHits().totalHits);

        } catch (IOException e) {
            log.error("query ES datas error", e);
        }
        return searchResponse;
    }

    /**
     * 处理查询数据
     *
     * @param search
     */
    private void handleEsDatas(SearchResponse search) {
        try {
            long totalHits = search.getHits().totalHits;
            log.info("----------totals 总条数为：------- {}", totalHits);
            List<EsSearchDto> res = new ArrayList<>();
            for (SearchHit searchHit : search.getHits()) {
                String sourceAsString = searchHit.getSourceAsString();
                EsSearchDto esSearchDto = JsonUtil.parseObject(sourceAsString, EsSearchDto.class);
                esSearchDto.setId(searchHit.getId());
                esSearchDto.setIndex(searchHit.getIndex());
                esSearchDto.setTypes(searchHit.getType());
                //log.info("------searchDto------- is {}", esSearchDto);

                if (StringUtils.isEmpty(esSearchDto.getBudgetPrice())) {
                    esSearchDto.setBudgetPrice("----");
                }

                if (StringUtils.isEmpty(esSearchDto.getBidPrice())) {
                    esSearchDto.setBidPrice("++++");
                }
                String mapKey = esSearchDto.getIndex() + "##" + esSearchDto.getTypes() + "##" + esSearchDto.getTitleText()
                        + "##" + esSearchDto.getInfoType() + "##" + esSearchDto.getType() + "##" + esSearchDto.getTos()
                        + "##" + esSearchDto.getCreateDate() + "##" + esSearchDto.getAreaCode() + "##" + esSearchDto.getBudgetPrice()
                        + "##" + esSearchDto.getBidPrice() + "##" + esSearchDto.getBidOrg();

                List<String> list = new ArrayList<>();
                list.add(esSearchDto.getId());
                if (ObjectUtils.isEmpty(globleMap)) {
                    globleMap.put(mapKey, list);
                } else {
                    if (!globleMap.containsKey(mapKey)) {
                        globleMap.put(mapKey, list);
                    } else {
                        List<String> list1 = globleMap.get(mapKey);
                        if (!list1.contains(esSearchDto.getId())) {
                            list1.add(esSearchDto.getId());
                            globleMap.put(mapKey, list1);
                        }
                    }
                }
//                log.info(sourceAsString);
                res.add(esSearchDto);
//                log.info("-------{}", esSearchDto);
            }
        } catch (Exception e) {
            log.error("hendle ES datas error ", e);
        }
    }

    /**
     * 删除ES中重复数据
     */
    private void deleteRepeatDate() {
        try {
            int tmp = 0;
            for (Map.Entry<String, List<String>> vo : globleMap.entrySet()) {
                String key = vo.getKey();
                String[] split = key.split("##");
                String index = split[0];
                String types = split[1];
                String title = split[2];
                List<String> value = vo.getValue();

                if (value.size() > 1) {
                    log.info("--------- is {}", value.toString());
                    tmp++;
                    for (int i = 1; i < value.size(); i++) {
                        String id = value.get(i);
                        DeleteRequest request = new DeleteRequest(index, types, id);
                        try {
                            //restHighLevelClient.delete(request);
                        } catch (Exception e) {
                            log.error("----------delete error -----", e);
                        }
                        log.warn("delete repeat data id is {}", value.get(i), title);
                        log.warn("delete repeat data id's title {}", title);
                    }
                    log.warn("repeat id {}", value.toString());
                }
            }
            log.info("------------不唯一 条数 {}", tmp);
        } catch (Exception e) {
            log.error("delete repeat datas err : ", e);
        }
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public String formatTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse(time);
        String format = simpleDateFormat.format(date);
        return format;
    }
}
