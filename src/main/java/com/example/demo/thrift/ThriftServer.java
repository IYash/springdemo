package com.example.demo.thrift;

import com.example.demo.thrift.generated.PersonService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: shiguang
 * @Date: 2021/6/3
 * @Description:
 **/
public class ThriftServer {
    public static void main(String[] args) throws Exception{
        //非阻塞的socket
        TProcessor processor = gettProcessor();
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(1).maxWorkerThreads(4).stopTimeoutVal(1000).stopTimeoutUnit(TimeUnit.MINUTES);

        //协议
        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));
        //启动server
        TServer server = new THsHaServer(arg);
        System.out.println("thrift server start");
        server.serve();
    }

    private static TProcessor gettProcessor() {
        Class clazz = null;
        String pname;
        String serviceClassName = "";
        PersonService.Iface service = new PersonServiceImpl();
        Class<?> serviceClass = service.getClass();
        Class<?>[] interfaces = serviceClass.getInterfaces(); //处理器，泛型真正服务的类型
        TProcessor processor = null;
        for(Class clz:interfaces){
            clazz = clz;
            String cname = clazz.getSimpleName();
            if("Iface".equals(cname)||"AsyncIface".equals(cname)){
                serviceClassName = clazz.getEnclosingClass().getName();
                if(cname.contains("Async")){
                    pname = serviceClassName + "$AsyncProcessor";
                }else pname = serviceClassName +"$Processor";
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    Class<?> pclass = classLoader.loadClass(pname);
                    if (TProcessor.class.isAssignableFrom(pclass)) {
                        Constructor<?> constructor = pclass.getConstructor(clazz);
                        processor = (TProcessor)constructor.newInstance(service);
                        break;
                    }
                } catch (Exception var21) {
                    System.out.println("error");
                }
            }
        }
        return processor;
    }
}
