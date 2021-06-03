package com.example.demo.js;

import redis.clients.jedis.Jedis;

/**
 * @Author: shiguang
 * @Date: 2021/4/20
 * @Description: 低质弹幕规则
 **/
public interface LowQualityRule {

    /**
     * 弹幕参数
     * @param lowQualityParam
     * @return
     */
    boolean match(LowQualityBulletStrategy.LowQualityParam lowQualityParam);

    Jedis cacheService();

    boolean needCompare(LowQualityBulletStrategy.LowQualityParam lowQualityParam);
}
