package com.elasticsearch.doc;

import com.elasticsearch.dto.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class CreateDoc {
    public static void main(String[] args) throws Exception{
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 创建文档
        IndexRequest indexRequest = new IndexRequest("order");
        // 指定id
        indexRequest.id("1003");
        // 创建数据，并转换成json格式
        Order order = new Order();
        order.setName("手机");
        order.setDesc("华为&huaweiP30");
        order.setCount(1);
        order.setPrice(5999.99);
        String json = new ObjectMapper().writeValueAsString(order);
        // 文档数据格式指定为json
        indexRequest.source(json, XContentType.JSON);
        // 创建文档
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        // 查看返回结果
        System.out.println(index.getResult());

        client.close();
    }
}
