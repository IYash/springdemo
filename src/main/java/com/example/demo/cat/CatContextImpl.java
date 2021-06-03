package com.example.demo.cat;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/3/27
 * @Description: Cat.context接口实现类，用于context调用链传递，相关方法Cat.logRemoteCall()和Cat.logRemoteServer()
 **/
public class CatContextImpl implements Cat.Context{

    private Map<String,String> properties = new HashMap<>();
    @Override
    public void addProperty(String key, String value) {
        properties.put(key,value);
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }
}
