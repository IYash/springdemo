package com.example.demo.remote;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: shiguang
 * @Date: 2021/3/26
 * @Description:
 **/
@Configuration
public class SakuraActivityServiceBuilder {

    private static final String BASE_URL = "http://shiguang.d.xiaohongshu.com";
    @Bean
    public SakuraActivityService sakuraActivityService() {
        Retrofit retrofit =  new Retrofit.Builder().baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(SakuraActivityService.class);
    }
}
