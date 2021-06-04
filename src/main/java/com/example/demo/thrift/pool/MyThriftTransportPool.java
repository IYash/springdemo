package com.example.demo.thrift.pool;


/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public class MyThriftTransportPool extends BaseCommonPool<Object>{

    public MyThriftTransportPool(PoolConfig poolConfig, PoolFactory<Object> poolFactory) {
        super(poolConfig, poolFactory);
    }
}
