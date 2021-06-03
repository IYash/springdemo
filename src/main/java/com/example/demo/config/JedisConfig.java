package com.example.demo.config;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @Author: shiguang
 * @Date: 2021/3/15
 * @Description: 建议使用redisTemplate方式，问题在于配置的唯一性，不太容易符合多个场景的redis的使用
 **/
@Configuration
public class JedisConfig {

    @Bean
    @ConfigurationProperties(prefix = "redis.pool")
    public JedisPoolProperties jedisPoolProperties(){
        return new JedisPoolProperties();
    }
    @Bean
    public JedisPoolConfig jedisPoolConfig(@Qualifier("jedisPoolProperties") JedisPoolProperties jedisPoolProperties){
        JedisPoolConfig poolConfig= new JedisPoolConfig();
        BeanUtils.copyProperties(jedisPoolProperties,poolConfig);
        return poolConfig;
    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);//默认使用localhost，6379，无密码
        return jedisConnectionFactory;
    }
}
