package com.elasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

public class SearchIndex {
    public static void main(String[] args) throws Exception{
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 查询索引
        GetIndexResponse order = client.indices().get(new GetIndexRequest("order"), RequestOptions.DEFAULT);
        // 打印相关信息; 索引的别名、结构、设置信息
        System.out.println(order.getAliases());
        System.out.println(order.getMappings());
        System.out.println(order.getSettings());

        client.close();
    }
}
