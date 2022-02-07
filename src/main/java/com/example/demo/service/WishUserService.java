package com.example.demo.service;

import com.example.demo.dal.WishUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: shiguang
 * @Date: 2021/12/10
 * @Description:
 **/
@Service
@Slf4j
public class WishUserService {
    @Resource
    WishUserMapper wishUserMapper;

    public void addAssociation(Long wishId,Long userId){
        wishUserMapper.addAssociation(wishId,userId);
    }

    public Long queryUserId(Long wishId){
        return wishUserMapper.queryUserId(wishId);
    }
}
