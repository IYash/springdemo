package com.example.demo.consistency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: shiguang
 * @Date: 2021/7/6
 * @Description:
 **/

public class Common {
    /**
     * 提交者数量
     */
    public static final int PROPOSER_COUNT = 2;


    /**
     *  接受者数量
     */
    public static final int ACCEPTOR_COUNT = 3;

    /**
     * 全局提案编号（初始值为1）
     */
    public static AtomicInteger proposerN=new AtomicInteger(0);


}
