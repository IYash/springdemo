package com.example.demo.service;

import com.example.demo.dal.WishAssociationMapper;
import com.example.demo.dal.WishMapper;
import com.example.demo.entity.WishAssociation;
import com.example.demo.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.demo.constant.RedisKey.MAX_LIKES;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
@Service
@Slf4j
public class WishAssociationService {
    @Resource
    private WishAssociationMapper wishAssociationMapper;
    @Resource
    private WishMapper wishMapper;

    Jedis activityCorvusCache = JedisUtil.manualInitJedis();

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void addAssociation(Long wishId,Long userId,Long likeUserId){
        WishAssociation association = WishAssociation.build(wishId,likeUserId,userId);
        wishAssociationMapper.addAssociation(association);
        wishMapper.updateLikes(userId,wishId);
        executorService.execute(()->sparkStateCal(userId));
    }

    public void sparkStateCal(Long userId){//及时性要求相对较低
        int likes = wishMapper.maxLikes(userId);
        String key = String.format(MAX_LIKES,userId);
        activityCorvusCache.setex(key,180,likes+"");
    }


}
