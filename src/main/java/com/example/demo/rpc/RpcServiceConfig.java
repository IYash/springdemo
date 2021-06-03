package com.example.demo.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shiguang
 * @Date: 2021/3/13
 * @Description:
 **/
@Configuration
public class RpcServiceConfig {

    @Bean
    public RpcServiceProxyFactory rpcServiceProxyFactory() {
        return new RpcServiceProxyFactory();
    }

    @Bean
    public RpcService rpcService() throws Exception{
        return rpcServiceProxyFactory().getObject();
    }
}