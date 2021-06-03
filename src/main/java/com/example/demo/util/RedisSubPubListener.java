package com.example.demo.util;

import redis.clients.jedis.JedisPubSub;

/**
 * @Author: shiguang
 * @Date: 2021/3/15
 * @Description:
 **/
public class RedisSubPubListener  extends JedisPubSub {
    public void onMessage(String channel, String message) {
        System.out.println(channel+" onMessage "+message);
    }

    public void onPMessage(String pattern, String channel, String message) {
    }

    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(channel+" onMessage "+subscribedChannels);
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
    }

    public void onPUnsubscribe(String pattern, int subscribedChannels) {
    }

    public void onPSubscribe(String pattern, int subscribedChannels) {
    }

    public void onPong(String pattern) {
    }
}
