package com.example.demo.consistency;

import lombok.Data;

import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/7/6
 * @Description:
 **/
@Data
public class Proposer {
    /**
     * 提议者id(唯一标识)
     */
    private int id;

    /**
     *  提案Map类型,key 为提案编号，value提案值
     */
    private volatile Map<Integer,String> proposals;

}
