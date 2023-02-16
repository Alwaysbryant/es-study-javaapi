package com.elasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

public class CreateIndex {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 创建索引
        CreateIndexResponse order = client.indices().create(new CreateIndexRequest("order"), RequestOptions.DEFAULT);
        // 创建结果
        boolean acknowledged = order.isAcknowledged();

        System.out.println("创建结果： " + (acknowledged ? "成功": "失败"));


        client.close();
    }
}
