package com.example.demo.thrift;

import com.example.demo.thrift.generated.Person;
import com.example.demo.thrift.generated.PersonService;
import com.example.demo.thrift.pool.MyThriftTransportFactory;
import com.example.demo.thrift.pool.MyThriftTransportPool;
import com.example.demo.thrift.pool.PoolConfig;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public class ThriftPoolClient {
    public static void main(String[] args) throws Exception {
        PoolConfig poolConfig = new PoolConfig();
        poolConfig.setMaxTotal(2);
        poolConfig.setMinTotal(2);
        MyThriftTransportPool pool = new MyThriftTransportPool(poolConfig,new MyThriftTransportFactory(PersonService.class));
        long start  = System.currentTimeMillis();
        PersonService.Iface proxyClient = null;
        String objName = null;
        for(int i=0;i<100;i++){
            try{
            proxyClient = (PersonService.Iface)pool.borrowObject();
            objName = proxyClient.toString().split("@")[1];
            System.out.println(objName);
            Person person = proxyClient.getPersonByUsername("张:"+i);
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            }finally {
                pool.returnObject(proxyClient,objName);
            }
        }
        System.out.println("cost time:"+ (System.currentTimeMillis()-start));
    }
}
