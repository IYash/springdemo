package com.example.demo.thrift.pool;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public interface PoolFactory<T> {

    T makeObject();

    void destroyObject(T t,String objName);
}
