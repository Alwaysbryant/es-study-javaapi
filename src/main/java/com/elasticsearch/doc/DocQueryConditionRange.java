package com.elasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class DocQueryConditionRange {
    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 条件查询
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("order");
        // 构建查询,
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // should = or must = and
        // 相当于： count = 1 && (price = 1999.0 || price = 2999.0)
        boolQueryBuilder.must(QueryBuilders.matchQuery("count", "1"));
        BoolQueryBuilder boolQueryBuilderOr = QueryBuilders.boolQuery();
        boolQueryBuilderOr.should(QueryBuilders.matchQuery("price", 1999.0));
        boolQueryBuilderOr.should(QueryBuilders.matchQuery("price", 2999.0));
        boolQueryBuilder.must(boolQueryBuilderOr);
//        searchSourceBuilder.query(boolQueryBuilder);
        // 范围查询, gt 大于 lt 小于 gte 大于等于 lte 小于等于
        BoolQueryBuilder boolQueryBuilderAnd = QueryBuilders.boolQuery();
        RangeQueryBuilder priceQuery = QueryBuilders.rangeQuery("price");
        priceQuery.gt(2000).lt(4000);
        boolQueryBuilderAnd.must(priceQuery);
        boolQueryBuilder.must(boolQueryBuilderAnd);
        searchSourceBuilder.query(boolQueryBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        client.close();
    }
}
