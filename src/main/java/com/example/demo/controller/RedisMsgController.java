package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.biz.CacheObject;
import com.example.demo.config.ValueConfig;
import com.example.demo.response.BaseResponse;
import com.example.demo.util.JedisUtil;
import io.micrometer.core.annotation.Counted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;


/**
 * @Author: shiguang
 * @Date: 2021/3/20
 * @Description: redis 发布消息
 **/
@RestController
public class RedisMsgController {

    @Autowired
    JedisUtil jedisUtil;
    @Autowired
    ValueConfig config;
    public static ScriptEngineManager engineManager = new ScriptEngineManager();
    ScriptEngine engine = engineManager.getEngineByName("javascript");
    @GetMapping("/postMsg")
    public BaseResponse postMsg(String msg){
        jedisUtil.publish(msg);
        return BaseResponse.success();
    }
    @GetMapping("/catchVal")
    public BaseResponse catchVal(){
        return new BaseResponse("0",config.getValue());
    }
    //hmset
    @GetMapping("/hmset")
    public BaseResponse hmset(String key){
        jedisUtil.hmset(key, CacheObject.build());
        return BaseResponse.success();
    }
    @GetMapping("/hget")
    public BaseResponse hget(String key,String field){
        String[] fields = {field};
        List<String> list = jedisUtil.fetchJedis().hmget(key,fields);
        return new BaseResponse("0", JSONObject.toJSONString(list));
    }

    @GetMapping("hgetall")
    public BaseResponse hgetall(String key){
        return new BaseResponse("0",jedisUtil.hgetall(key)+"");
    }
    @GetMapping("/metric")
    @Counted(value="counter.sample")
    public BaseResponse metric(){
        return BaseResponse.success();
    }

    @GetMapping("/jsTest")
    public BaseResponse jsTest(String key) throws Exception{
        jedisUtil.fetchJedis().set("test","ah啊哈");
        String function = "function checkFormat(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\",\"g\");" +
                "var len= s.length; if(!reg.test(s.charAt(len-1))) return true;" +
                "var subStr=\"R]\"; if(s.match(subStr)) return true;" +
                "return !reg.test(s);}";
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        String value = jedisUtil.fetchJedis().get("test");
        System.out.println(value);
        Object res = invocable.invokeFunction("checkFormat",value);
        System.out.println(res);

        return BaseResponse.success();
    }
}
