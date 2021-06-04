package com.example.demo.util;

import com.example.demo.config.JedisConfig;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

/**
 * @Author: shiguang
 * @Date: 2021/3/15
 * @Description:
 **/
@Component
@Import(JedisConfig.class)
@Data
public class JedisUtil implements InitializingBean {
    @Resource
    JedisConnectionFactory jedisConnectionFactory;
    JedisPool jedisPool;
    @Override
    public void afterPropertiesSet() throws Exception {
        Field poolField = ReflectionUtils.findField(JedisConnectionFactory.class, "pool");
        ReflectionUtils.makeAccessible(poolField);
        jedisPool = (JedisPool) ReflectionUtils.getField(poolField, jedisConnectionFactory);
    }
    public Jedis fetchJedis(){
        return jedisPool.getResource();
    }
    public void publish(String msg){
        fetchJedis().publish("test",msg);
    }
    public void hmset(String key,Map<String, String> cacheMap){
        Jedis jedis = fetchJedis();
        jedis.hmset(key,cacheMap);
    }
    public int hgetall(String key){
        return fetchJedis().hgetAll(key).size();
    }
    public int inc(){
        return new Random().nextInt(1000);
    }

    //手动使用redis
    public static Jedis manualInitJedis(){
        return new Jedis("localhost",6379);
    }
}
