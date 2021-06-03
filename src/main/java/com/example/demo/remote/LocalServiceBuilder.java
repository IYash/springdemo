package com.example.demo.remote;

import com.example.demo.cat.CatRestInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;

/**
 * @Author: shiguang
 * @Date: 2021/3/26
 * @Description:
 **/
@Configuration
public class LocalServiceBuilder {

    private static final String BASE_URL = "http://localhost:8081";
    @Bean
    public LocalService localService() {
        Retrofit retrofit =  new Retrofit.Builder().baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(LocalService.class);
    }
    //临时添加restTemplate
    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 保存和传递调用链上下文
        restTemplate.setInterceptors( Collections.singletonList(new CatRestInterceptor()));
        return restTemplate;
    }
}
