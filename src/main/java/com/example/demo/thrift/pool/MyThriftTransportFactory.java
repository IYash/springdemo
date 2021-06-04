package com.example.demo.thrift.pool;

import com.example.demo.thrift.generated.PersonService;
import com.google.common.collect.Maps;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public class MyThriftTransportFactory implements PoolFactory<Object>{

    private Map<String,TTransport> tTransportMap = Maps.newConcurrentMap();

    public MyThriftTransportFactory(){}
    @Override
    public Object makeObject()  {
        TTransport transport = new TFramedTransport(new TSocket("localhost",8899),600);
        TProtocol protocol = new TCompactProtocol(transport);
        try {
            transport.open();
            Class<?> thriftClass = PersonService.class;
            ClassLoader classLoader = thriftClass.getClassLoader();
            String service = thriftClass.getName();
            Class<?> thriftClientClass = classLoader.loadClass(service + "$Client");
            Constructor<?> constructor = thriftClientClass.getConstructor(TProtocol.class);
            Object thriftServiceClient =  constructor.newInstance(protocol);
            Class[] interfaces = thriftClientClass.getInterfaces();
            Object obj =  Proxy.newProxyInstance(classLoader,
                    interfaces,
                    (proxy,method,args1)->{
                        return method.invoke(thriftServiceClient,args1);
                    });
            System.out.println(obj.toString().split("@")[1]+"=====init=====");
            tTransportMap.put(obj.toString().split("@")[1],transport);
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroyObject(Object obj,String objName) {
        TTransport transport = tTransportMap.get(objName);
        if(Objects.nonNull(transport))
        transport.close();
    }
}
