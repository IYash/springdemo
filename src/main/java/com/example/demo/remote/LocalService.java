package com.example.demo.remote;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @Author: shiguang
 * @Date: 2021/3/27
 * @Description: 用于本地链路监控的服务提供端
 **/
public interface LocalService {
    @GET("/demo/catchVal")
    Call<JsonObject> catchVal();
}
