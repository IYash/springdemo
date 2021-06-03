package com.example.demo.thrift;

import com.example.demo.thrift.generated.Person;
import com.example.demo.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

/**
 * @Author: shiguang
 * @Date: 2021/6/3
 * @Description:
 **/
public class ThriftClient {

    public static void main(String[] args) throws Exception{
        TTransport transport = new TFramedTransport(new TSocket("localhost",8899),600);
        TProtocol protocol = new TCompactProtocol(transport);
        try{
            PersonService.Iface proxyClient = (PersonService.Iface)getClient(protocol);
            transport.open();
            Person person = proxyClient.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            transport.close();
        }
    }
    public static Object getClient(TProtocol protocol) throws Exception{
        Class<?> thriftClass = PersonService.class;
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
