package com.example.demo.rpc;

import com.example.demo.util.JedisUtil;
import com.example.demo.util.RedisSubPubListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author: shiguang
 * @Date: 2021/3/13
 * @Description:
 **/
public class RpcServiceProxyFactory implements InitializingBean, FactoryBean<RpcService>, ApplicationContextAware {


    public static ApplicationContext ctx =null;
    public RpcService rpcClient;
    @Autowired
    JedisUtil jedisUtil;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        rpcClient = (RpcService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{RpcService.class} , makeProxyHandler());
        //订阅服务
        new Thread(()-> {
            Jedis jedis = jedisUtil.fetchJedis();
            jedis.subscribe(new RedisSubPubListener(),"test");
            }
      ).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    @Override
    public RpcService getObject() throws Exception {
        //基于jdk动态代理实现
        return rpcClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RpcService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    private InvocationHandler makeProxyHandler(){
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            //远程通信配置
            System.out.println("invoke for "+method.getName());
            return "hello rpc " + args[0];
        };
        return invocationHandler;
    }
}
