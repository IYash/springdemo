package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/12/10
 * @Description:
 **/
@Data
public class UserProfile {
    private boolean posted;
    private int sparkLevel;

    public static UserProfile build(boolean posted,int sparkLevel){
        UserProfile up = new UserProfile();
        up.setPosted(posted);
        up.setSparkLevel(sparkLevel);
        return up;
    }
}
