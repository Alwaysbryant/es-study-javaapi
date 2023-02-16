package com.elasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class DocQueryCondition {
    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 条件查询
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("order");
        // 构建查询, QueryBuilders.matchQuery()查询匹配的数据
        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchQuery("name", "手机"));
        // 分页
        query.from(0).size(3);
        // 排序 desc降序，asc升序
        query.sort("price", SortOrder.DESC);
        // 过滤显示的字段
        // 包含的字段
        String[] includes = {"name", "price"};
        // 排除的字段
        String[] excludes = {"count"};
        query.fetchSource(includes, excludes);
        searchRequest.source(query);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        client.close();
    }
}
