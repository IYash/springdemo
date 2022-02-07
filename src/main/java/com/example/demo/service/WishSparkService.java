package com.example.demo.service;

import com.example.demo.dal.WishSparkMapper;
import com.example.demo.entity.WishSparkDO;
import com.example.demo.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

import java.util.List;

import static com.example.demo.constant.RedisKey.MAX_LIKES;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
@Service
@Slf4j
public class WishSparkService {

    @Resource
    private WishSparkMapper wishSparkMapper;

    @Value("#{'${spark.strategy:20,100,500}'.split(',')}")
    private List<Integer> standard;

    Jedis activityCorvusCache = JedisUtil.manualInitJedis();

    public void sparkLevelModify(Long userId,int level){
        wishSparkMapper.addOrUpdateSparkLevel(userId,level);
    }

    public int querySparkLevel(Long userId){
        String key = String.format(MAX_LIKES,userId);
        String likeStr = activityCorvusCache.get(key);
        int count = 0;
        int level = 0;
        if(StringUtils.isNotEmpty(likeStr)){
            int likes = Integer.valueOf(likeStr);
            level = WishSparkDO.levelCal(likes,standard);
            if(level > 0){
                count = wishSparkMapper.fireWorkCheck(userId,level);
            }
        }
        return count == 0 ? level: 0;
    }
}
