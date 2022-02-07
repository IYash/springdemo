package com.example.demo.constant;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
public class RedisKey {
    /**
     * 用户所有愿望最大点赞数
     */
    public static final String MAX_LIKES = "string:user_wish_like_max_fire:%s";
    /**
     * 不安全用户列表
     */
    public  static final String EXCLUDE_KEY = "string:exclude_wishid:%s";
    /**
     * 用户最近发布愿望时间
     */
    public  static final String USER_WISH_POSTED = "string:user_wish_posted:%s";
    /**
     * 用户个人愿望数据
     */
    public static final String USER_WISH_JSON = "string:user_wish_json:%s";
    /**
     * 愿望条数统计
     */
    public static final String POSTED_WISHES = "string:posted_wishes";
    /**
     * id生成器
     */
    public  static final String idGeneratorKey =  "string:idgenerator";

}
