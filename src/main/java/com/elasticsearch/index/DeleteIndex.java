package com.elasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

public class DeleteIndex {
    public static void main(String[] args) throws Exception{
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 删除索引
        AcknowledgedResponse order = client.indices().delete(new DeleteIndexRequest("order"), RequestOptions.DEFAULT);

        boolean acknowledged = order.isAcknowledged();

        System.out.println("删除索引结果： " + (acknowledged ? "successful": "failure"));

        client.close();
    }
}
