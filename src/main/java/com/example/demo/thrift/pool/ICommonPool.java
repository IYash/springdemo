package com.example.demo.thrift.pool;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public interface ICommonPool<T> {

    T borrowObject() throws Exception;

    void returnObject(T object,String objName) throws Exception;
}
