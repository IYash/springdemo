package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.UserProfile;
import com.example.demo.entity.WishDO;
import com.example.demo.entity.WishDto;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.WishAssociationService;
import com.example.demo.service.WishService;
import com.example.demo.service.WishSparkService;
import com.example.demo.service.WishUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2021/12/10
 * @Description:
 **/
@RestController
public class WishController {
    @Resource
    WishService wishService;
    @Resource
    WishAssociationService wishAssociationService;
    @Resource
    WishSparkService wishSparkService;
    @Resource
    WishUserService wishUserService;

    /**
     * 愿望池，支持分享进入
     * @param cursor
     * @param userId
     * @param wishId
     * @return
     */
    @GetMapping("/wish/view")
    public BaseResponse view(String cursor,Long userId,Long wishId){
        WishDto dto = wishService.view(cursor,userId,wishId);
        WishDO shareDO = dto.getShareDO();
        if(Objects.nonNull(shareDO)){
            Long postUserId = shareDO.getUserId();
            wishId = shareDO.getId();
            wishAssociationService.addAssociation(wishId,postUserId, userId);
        }
        return new BaseResponse("200", JSONObject.toJSONString(dto));
    }

    @PostMapping("/wish/batchPost")
    public BaseResponse batchPost(int batch,String content,Long userId){
        wishService.batchPostWish(content,userId,batch);
        return BaseResponse.success();
    }

    /**
     * 愿望提交
     * @param content
     * @param userId
     * @return
     */
    @PostMapping("/wish/post")
    public BaseResponse post(String content,Long userId){
        wishService.postWish(content,userId);
        return BaseResponse.success();
    }

    /**
     * 个人愿望列表
     * @param userId
     * @return
     */
    @GetMapping("/wish/self")
    public BaseResponse selfPool(Long userId){
        return new BaseResponse("200",JSONObject.toJSONString(wishService.selfPool(userId)));
    }
    /**
     * 用户特征查询
     */
    @GetMapping("/wish/feature")
    public BaseResponse feature(Long userId){
        int level = wishSparkService.querySparkLevel(userId);
        boolean posted = wishService.postedCheck(userId);
        UserProfile up = UserProfile.build(posted,
                level);
        return new BaseResponse("200",JSONObject.toJSONString(up));
    }

    /**
     * 礼花播放
     */
    @PostMapping("/wish/spark")
    public BaseResponse spark(Long userId,Integer sparkLevel){
        wishSparkService.sparkLevelModify(userId,sparkLevel);
        return BaseResponse.success();
    }

    /**
     * 状态变更
     */
    @PostMapping("/wish/verify")
    public BaseResponse verify(Long userId,Long wishId,int status){
        wishService.updateStatus(userId,wishId,status);
        return BaseResponse.success();
    }

    /**
     * 点赞
     */
    @PostMapping("/wish/like")
    public BaseResponse like(Long userId, Long wishId,Long wishLikeUserId){
        wishAssociationService.addAssociation(wishId,userId,wishLikeUserId);
        return BaseResponse.success();
    }

    /**
     * 已发布愿望数
     */
    @GetMapping("/wish/posted")
    public BaseResponse posted(){
        return new BaseResponse("200",wishService.posted()+"");
    }
}
