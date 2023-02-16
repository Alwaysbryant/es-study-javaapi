package com.elasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class DocQuery {
    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        // 批量插入数据
//        BulkRequest bulkRequest = new BulkRequest();
//        bulkRequest.add(new IndexRequest().index("order").id("1004").source(XContentType.JSON, "name", "电脑", "desc", "笔记本", "count", 1, "price", 12999.00));
//        bulkRequest.add(new IndexRequest().index("order").id("1005").source(XContentType.JSON, "name", "电风扇", "desc", "美的电风扇", "count", 1, "price", 1999.00));
//        bulkRequest.add(new IndexRequest().index("order").id("1006").source(XContentType.JSON, "name", "手表", "desc", "Apple Watch S8", "count", 1, "price", 2999.00));
//        bulkRequest.add(new IndexRequest().index("order").id("1007").source(XContentType.JSON, "name", "耳机", "desc", "AirPods", "count", 1, "price", 1999.00));
//        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//        TimeValue took = bulk.getTook();
//        System.out.println(took);

        // 条件查询
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("order");
        // 构建查询, QueryBuilders.matchAllQuery()查询所有数据
        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        searchRequest.source(query);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        client.close();
    }
}
