package com.elasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

public class DocQueryFuzzyAndHighlight {
    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        // 模糊查询
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("order");
        // 构建模糊查询,
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // Fuzziness.TWO 指定条件匹配的偏差量
        FuzzyQueryBuilder fuzziness = QueryBuilders.fuzzyQuery("name", "手表").fuzziness(Fuzziness.ONE);
        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>").postTags("</font>").field("name");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(fuzziness);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        client.close();
    }
}
