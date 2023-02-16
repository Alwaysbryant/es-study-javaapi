package com.elasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

public class DocQueryAggs {
    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 聚合查询
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("order");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 聚合函数
        // 最大值
//        AggregationBuilder aggregationBuilder = AggregationBuilders.max("max_age").field("price");
        // 分组
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("max_group").field("price");
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        client.close();
    }
}
