package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
@Data
public class WishAssociation {
    private Long wishId;
    private Long likeUserId;
    private Long userId;//这个字段是愿望表的分表字段
    private Date createTime;
    private Date updateTime;

    public static WishAssociation build(Long wishId,Long likeUserId,Long userId){
        WishAssociation association = new WishAssociation();
        association.setWishId(wishId);
        association.setUserId(userId);
        association.setLikeUserId(likeUserId);
        return association;
    }
}
