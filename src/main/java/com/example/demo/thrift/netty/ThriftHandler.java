package com.example.demo.thrift.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
@Slf4j
public class ThriftHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final ThriftExecutor executor;

    public ThriftHandler(ThriftExecutor executor) {
        this.executor = executor;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String channelId = channel.id().asShortText();

        log.trace("channelActive: channelId={}", channelId);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String channelId = channel.id().asShortText();

        log.trace("channelInactive: channelId={}", channelId);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught:", cause);
    }

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        executor.executeAction(ctx, msg);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;

            if (e.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.channel().close();
            }
        }
    }

}
