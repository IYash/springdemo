package com.example.demo.entity;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2021/12/6
 * @Description:
 **/
@Data
public class WishDO {
    private Long id;
    private Long userId;
    private String content;
    private Integer likes;
    private Integer status;//审核状态，用于内容审核状态的变更；
    private Integer viewType;//用户设置的可见性
    private Date createTime;
    private Date updateTime;
    private Double score;
    private Integer acType;//活动类型，需要支持多个活动
    public static WishDO build(Long id,Long userId,String content){
        WishDO vo = new WishDO();
        vo.setId(id);
        vo.setUserId(userId);
        vo.setContent(content);
        return vo;
    }
    public double score(){
        //createTime+likes
        if(status == 1){
            score =  scoreByTime()*0.4 + scoreByLikes()*0.6;
        } else {
            score = scoreByLikes();
        }
        return score;
    }
    //不同指标需要进行归一处理，给个区间，超出区间范围的给默认值,假设为3天
    public double scoreByTime(){//系数0.4，目标时间越短比例越大
        double score = 1-(System.currentTimeMillis()-getCreateTime().getTime())/1000/86400;
       return  score<0?0.01:score;
    }
    public double scoreByLikes(){//目前只使用系数0.6,假设基准为100，基准设定合理，否则容易出现指标倾斜
        double score = getLikes()/1000;
        return getLikes()/1000>1?0.99:score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishDO wishDO = (WishDO) o;
        return Objects.equals(id, wishDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
