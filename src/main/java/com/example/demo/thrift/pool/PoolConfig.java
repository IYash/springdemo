package com.example.demo.thrift.pool;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
@Data
public class PoolConfig {
    private int maxTotal = 8;
    private int minTotal = 0;
}
