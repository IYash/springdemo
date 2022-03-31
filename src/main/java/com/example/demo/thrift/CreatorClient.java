package com.example.demo.thrift;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xiaohongshu.infra.rpc.base.Context;
import com.xiaohongshu.sns.rpc.creator.CreatorService;
import com.xiaohongshu.sns.rpc.creator.WeekTopLabelRequest;
import lombok.Data;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2022/3/16
 * @Description:
 **/
public class CreatorClient {

    public static void main(String[] args) throws Exception{
        TTransport transport = new TFramedTransport(new TSocket("localhost",4588),600);
        TProtocol protocol = new TBinaryProtocol(transport);
        transport.open();
        try{
            String json = "62385b38000000000000c972";//buildJson();
            System.out.println(json);
            CreatorService.Iface proxyClient = (CreatorService.Iface)getClient(protocol);
            proxyClient.weekTopLabelInfos(new Context(),new WeekTopLabelRequest().setUserId(json));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            transport.close();
        }
    }
    public static Object getClient(TProtocol protocol) throws Exception{
        Class<?> thriftClass = CreatorService.class;
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
    @Data
    static class Msg{
        String bizId;
        String bizType;
        List<DataInfo> dataList;
    }
    @Data
    public static class DataInfo{
        private int type;
        private int value;
    }

    public static String buildJson(){
        Msg msg = new Msg();
        msg.setBizId("526a6248b4c4d612b49a70a4");
        msg.setBizType("NOTE");
        DataInfo info = new DataInfo();
        info.setType(20002);
        info.setValue(2);
        msg.setDataList(Lists.newArrayList(info));
        return JSONObject.toJSONString(msg);
    }
}
