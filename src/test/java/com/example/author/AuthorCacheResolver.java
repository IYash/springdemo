package com.example.author;

import com.example.demo.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @Author: shiguang
 * @Date: 2022/3/9
 * @Description:
 **/
@Component
@Slf4j
public class AuthorCacheResolver {

    //@Resource
    Jedis creatorCorvusRedis = JedisUtil.manualInitJedis();

    /**
     * 缓存设置
     *
     * @param uid
     * @param value
     */
    public void setAuthorFeature(String uid, String field, String value) {
        String key = userFeatureKey(uid);
        creatorCorvusRedis.hset(key, field, value);
        creatorCorvusRedis.expire(key, 86400);
    }

    /**
     * @param uid
     * @param field
     * @return
     */
    public String queryAuthorFeature(String uid, String field) {
        return creatorCorvusRedis.hget(userFeatureKey(uid), field);
    }

    private String userFeatureKey(String uid) {
        return String.format(AuthorKeys.FIRST_PUBLISH_VERIFIED, uid);
    }
}
