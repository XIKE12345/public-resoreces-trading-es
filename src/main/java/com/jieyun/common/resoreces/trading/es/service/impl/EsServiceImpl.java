package com.jieyun.common.resoreces.trading.es.service.impl;

import com.jieyun.common.resoreces.trading.es.model.Pager;
import com.jieyun.common.resoreces.trading.es.utils.AppPubResConst;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author ：ren
 * @date ：Created in 2019/5/29 17:12
 */
@Service
public class EsServiceImpl {

    /**
     * 创建查询条件
     *
     * @param indices       索引字段
     * @param types         类型
     * @param includeFields 要查的字段
     * @param sortFiled     排序字段
     * @param sortOrder     排序方式 默认倒叙
     * @param pager         分页字段
     * @param aggregation   聚合函数
     * @param highLightType  高亮类型
     * @param queryBuilders 查询的DSL
     * @return void
     * @author ren
     * @date 2019/5/29 16:11
     */

    public SearchRequest getSearchRequest(String[] indices, String[] types, String[] includeFields,
                                          String sortFiled, SortOrder sortOrder, Pager pager,String highLightType,
                                          AggregationBuilder aggregation, QueryBuilder queryBuilders){
        //搜索位置设置
        SearchRequest searchRequest = new SearchRequest().indices(indices).types(types);
        if(!ObjectUtils.isEmpty(indices)&&!ObjectUtils.isEmpty(types)){
            searchRequest.indices(indices).types(types);
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        /*高亮*/
        if(!ObjectUtils.isEmpty(highLightType)){
            HighlightBuilder highlightBuilder = this.setHighLightInfo(highLightType);
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        searchSourceBuilder.query(queryBuilders);
        //分页
        if(pager != null){
            searchSourceBuilder.from(pager.getStartRow());
            searchSourceBuilder.size(pager.getTotalSize());
        }
        //查询字段过滤
        if(!ObjectUtils.isEmpty(includeFields)){
            searchSourceBuilder.fetchSource(includeFields,null);
        }
        //排序
        if(!ObjectUtils.isEmpty(sortFiled)){
            if(sortOrder != null){
                searchSourceBuilder.sort(sortFiled,sortOrder);
            }else{
                searchSourceBuilder.sort(sortFiled);
            }

        }
        //聚合
        if(aggregation != null){
            searchSourceBuilder.aggregation(aggregation);

        }

        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    private HighlightBuilder setHighLightInfo(String highLightType){
        /*高亮*/
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        if(AppPubResConst.HighLightType.ANNOUNCEMENT_SEARCH.equals(highLightType)){
            HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title_text");
            highlightTitle.fragmentSize(200);
            highlightBuilder.field(highlightTitle);

            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content_text");
            highlightContent.fragmentSize(10000);
            highlightBuilder.field(highlightContent);
        }
        if(AppPubResConst.HighLightType.TITLE_SEARCH.equals(highLightType)){
            HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title_text");
            highlightTitle.fragmentSize(200);
            highlightBuilder.field(highlightTitle);
        }

        if(AppPubResConst.HighLightType.ALL_SEARCH.equals(highLightType)){
            HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title_text");
            highlightTitle.fragmentSize(300);
            highlightBuilder.field(highlightTitle);

            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content_text");
            highlightContent.fragmentSize(500);
            highlightBuilder.field(highlightContent);
        }

        if(AppPubResConst.HighLightType.COMPANY_SEARCH.equals(highLightType)){
            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("compname_text_kw");
            highlightContent.fragmentSize(200);
            highlightBuilder.field(highlightContent);
        }

        if(AppPubResConst.HighLightType.TENDER_SEARCH.equals(highLightType)){
            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("tender_org_text_kw");
            highlightContent.fragmentSize(200);
            highlightBuilder.field(highlightContent);
        }



        highlightBuilder.preTags("<highLight>");
        highlightBuilder.postTags("</highLight>");
        return highlightBuilder;
    }


}
