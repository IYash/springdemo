package com.example.demo.thrift;

import com.example.demo.thrift.generated.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import java.util.concurrent.TimeUnit;

/**
 * @Author: shiguang
 * @Date: 2021/6/3
 * @Description:
 **/
public class ThriftServer {
    public static void main(String[] args) throws Exception{
        //非阻塞的socket
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(1).maxWorkerThreads(4).stopTimeoutVal(1000).stopTimeoutUnit(TimeUnit.MINUTES);
        //处理器，泛型真正服务的类型
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        //协议
        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));
        //启动server
        TServer server = new THsHaServer(arg);
        System.out.println("thrift server start");
        server.serve();
    }
}
