package com.elasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class UpdateDoc {
    public static void main(String[] args) throws Exception{
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        // 更新文档
        UpdateRequest request = new UpdateRequest();
        // 执行需要修改的索引以及文档id
        request.index("order").id("1002");
        // 局部修改
        request.doc(XContentType.JSON, "name", "智能手机");
        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update.getResult());

        client.close();
    }
}
