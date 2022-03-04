package com.example.demo.thrift;

import com.example.demo.thrift.generated.Person;
import com.example.demo.thrift.generated.PersonService;
import com.xiaohongshu.infra.rpc.base.Context;
import com.xiaohongshu.sns.rpc.activityother.ActivityOtherService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

/**
 * @Author: shiguang
 * @Date: 2022/2/26
 * @Description:
 **/
public class ActivityOtherClient {

    public static void main(String[] args) throws Exception{
        TTransport transport = new TFramedTransport(new TSocket("localhost",4588),600);
        TProtocol protocol = new TBinaryProtocol(transport);
        transport.open();
        try{
            ActivityOtherService.Iface proxyClient = (ActivityOtherService.Iface)getClient(protocol);
            proxyClient.queryHistoryReports(new Context());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            transport.close();
        }
    }
    public static Object getClient(TProtocol protocol) throws Exception{
        Class<?> thriftClass = ActivityOtherService.class;
        ClassLoader classLoader = thriftClass.getClassLoader();
        String service = thriftClass.getName();
        Class<?> thriftClientClass = classLoader.loadClass(service + "$Client");
        Constructor<?> constructor = thriftClientClass.getConstructor(TProtocol.class);
        Object thriftServiceClient =  constructor.newInstance(protocol);
        Class[] interfaces = thriftClientClass.getInterfaces();
        return Proxy.newProxyInstance(classLoader,
                interfaces,
                (proxy,method,args1)->{
                    return method.invoke(thriftServiceClient,args1);
                });
    }
}
