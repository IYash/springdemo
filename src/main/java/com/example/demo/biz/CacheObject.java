package com.example.demo.biz;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: shiguang
 * @Date: 2021/3/22
 * @Description:
 **/
@Data
public class CacheObject {
    private String uuid;
    public static Map<String,String> build(){
        Map<String,String> map = new HashMap<>();
        int i=0;
        while(i++<10000){
            CacheObject object = new CacheObject();
            object.uuid = UUID.randomUUID().toString();
            map.put(object.uuid, JSONObject.toJSONString(object));
        }
        return map;
    }
}
