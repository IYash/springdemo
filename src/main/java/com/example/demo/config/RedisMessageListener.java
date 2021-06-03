package com.example.demo.config;

import com.example.demo.util.Receiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @Author: shiguang
 * @Date: 2021/3/20
 * @Description: redis 消息订阅
 **/
@Configuration
public class RedisMessageListener {

    @Bean
    RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory,MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        //订阅了一个叫chat 的通道
        container.addMessageListener(listenerAdapter, new PatternTopic("test"));
        //这个container 可以添加多个 messageListener
        //container.addMessageListener(listenerAdapter, new PatternTopic("这里是监听的通道的名字"));
        return container;
    }
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver){
       return  new MessageListenerAdapter(receiver,"receiveMessage");
    }
}
