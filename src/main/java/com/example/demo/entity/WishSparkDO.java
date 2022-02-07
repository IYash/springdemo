package com.example.demo.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
@Data
public class WishSparkDO {
    private Long id;
    private Long userId;
    private Integer sparkLevel;//1,2,3三个等级
    private Date createTime;
    private Date updateTime;

    public static int levelCal(int likes, List<Integer> standard){
        if(likes>standard.get(2)) {return 3;}
        if(likes>standard.get(1)) {return 2;}
        if(likes>standard.get(0)) {return 1;}
        return 0;
    }
}
