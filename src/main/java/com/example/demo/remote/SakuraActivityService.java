package com.example.demo.remote;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @Author: shiguang
 * @Date: 2021/3/26
 * @Description: 仅适用于rpc调用不到的场景
 **/
public interface SakuraActivityService {
    @GET("/web_api/sns/v1/activity/note_arranged")
    Call<JsonObject> demoHttpFetch();
}
