package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shiguang
 * @Date: 2021/3/9
 * @Description:
 **/
@Configuration
public class ElasticsearchConfig {
    @Value("${elasticsearch.hostName:localhost}")
    private String localHostName;
    @Value("${elasticsearch.port:9200}")
    private int  port;
    @Bean
    public RestHighLevelClient localEsClient(){
        HttpHost httpHost = new HttpHost(localHostName,port,"http");
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
