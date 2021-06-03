package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/3/9
 * @Description:
 **/
@Slf4j
public class ElasticSearchTest {

    private String localHostName="127.0.0.1";

    private int  port = 9200;

    private RestHighLevelClient client = null;
    private static final String INDEX_NAME = "danmu_v1";
    private static final String ES_TYPE_NAME = "item";

    @Before
    public void localEsClient(){
        HttpHost httpHost = new HttpHost(localHostName,port,"http");
        client = new RestHighLevelClient(RestClient.builder(httpHost));

    }
    //创建索引
    @Test
    public void testIndex() throws Exception{
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        CreateIndexResponse response = client.indices().create(request,RequestOptions.DEFAULT );
        if(response.isAcknowledged()) log.info("create index success");
    }
    @Test
    public void testCreateItem() throws IOException {
        IndexRequest request = new IndexRequest(INDEX_NAME);
        request.id("1");
        Map<String,Object> m = new HashMap<>();
        m.put("name","test0");
        m.put("level",0);
        m.put("text","hello world");
        String jsonString = JSONObject.toJSONString(m);
        request.source(jsonString, XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }
    @Test
    public void testQuery(){

    }
}
