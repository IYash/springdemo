package com.example.demo.thrift.pool;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public class BaseCommonPool<T> implements ICommonPool<T>{

    private LinkedBlockingQueue<T> pool;
    private PoolConfig poolConfig;
    private PoolFactory<T> poolFactory;

    public BaseCommonPool(PoolConfig poolConfig, PoolFactory<T> poolFactory){
        this.poolConfig = poolConfig;
        this.poolFactory = poolFactory;
        initCommonPool();
    }
    private void initCommonPool() {
        //代表池的大小
        pool = new LinkedBlockingQueue<T>(poolConfig.getMaxTotal());
        while (poolConfig.getMinTotal() > pool.size()){
            T obj = poolFactory.makeObject();
            pool.offer(obj);
        }
    }

    @Override
    public T borrowObject() throws Exception {
        T obj = pool.poll();
        if(obj != null){
            return obj;
        }
        return poolFactory.makeObject();
    }

    @Override
    public void returnObject(T object,String objName) throws Exception {
        if(!pool.offer(object)){
            poolFactory.destroyObject(object,objName);
        }
    }
}
