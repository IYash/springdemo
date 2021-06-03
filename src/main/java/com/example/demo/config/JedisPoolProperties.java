package com.example.demo.config;

import lombok.Data;


/**
 * @Author: shiguang
 * @Date: 2021/3/15
 * @Description:
 **/
@Data
public class JedisPoolProperties {
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private long maxWaitMillis;
    private boolean LIFO;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
}
