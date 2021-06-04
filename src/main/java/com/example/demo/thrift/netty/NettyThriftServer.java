package com.example.demo.thrift.netty;

import com.example.demo.thrift.PersonServiceImpl;
import com.example.demo.thrift.generated.PersonService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;

import static com.example.demo.thrift.netty.Constant.*;
import static io.netty.channel.ChannelOption.SO_BACKLOG;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
@Slf4j
public class NettyThriftServer {
    private final int listenPort;

    private final ServerBootstrap serverBootstrap;

    private Channel listenChannel;

    public NettyThriftServer(int listenPort) {
        TMultiplexedProcessor processor = new TMultiplexedProcessor();

        processor.registerProcessor("PersonService", new PersonService.Processor<PersonServiceImpl>(new PersonServiceImpl()));

        this.listenPort = listenPort;

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast("idleStateHandler",
                        new IdleStateHandler(IDLE_TIMEOUT, IDLE_TIMEOUT, 0));

                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(MAX_CONTENT_LENGTH, 0, PKG_HEAD_LEN));

                pipeline.addLast("gatewayHandler", new ThriftHandler(new ThriftExecutor(processor)));
            }
        });

        serverBootstrap.option(SO_BACKLOG, SOCK_BACKLOG);

        log.info("initiate: listenPort=" + listenPort);
    }

    public boolean startListen() {
        try {
            listenChannel = serverBootstrap.bind(listenPort).sync().channel();

            log.info("finish to startListen. listenPort=" + listenPort);

            return true;
        } catch (Exception e) {
            log.error("fail to startListen. listenPort=" + listenPort, e);
        }

        return false;
    }

    public void stopListen() {
        try {
            if (listenChannel != null) {
                listenChannel.disconnect().get();

                log.info("finish to stopListen. listenPort=" + listenPort);
            }
        } catch (Exception e) {
            log.error("fail to stopListen. listenPort=" + listenPort, e);
        }
    }

    public static void main(String[] args) {
        NettyThriftServer server = new NettyThriftServer(9090);
        server.startListen();
    }


}
