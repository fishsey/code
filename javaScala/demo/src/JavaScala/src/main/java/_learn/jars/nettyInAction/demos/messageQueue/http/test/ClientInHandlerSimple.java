package _learn.jars.nettyInAction.demos.messageQueue.http.test;


import _learn.jars.nettyInAction.demos.messageQueue.http.tools.HttpRequestUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @project: nettygame
 * @Title: ClientInHandler.java
 * @Package: cpgame.nettygame
 * @author: chenpeng
 * @email: 46731706@qq.com
 * @date: 2015年8月27日 上午9:48:49
 * @description:
 * @version:
 */
public class ClientInHandlerSimple extends SimpleChannelInboundHandler<FullHttpResponse>
{
    public AtomicInteger num = new AtomicInteger(0);
    public String HEARTBEAT_SEQUENCE = "HEARTBEAT";

    Client client;

    public ClientInHandlerSimple(Client client)
    {
        this.client = client;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            System.out.println("userEventTriggered " + num.getAndIncrement());
            ChannelFuture channelFuture = ctx.channel().writeAndFlush(HttpRequestUtils.getHttpRequest(client.host, client.port, HEARTBEAT_SEQUENCE));
            channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("send init message . . . ");
        DefaultFullHttpRequest request = HttpRequestUtils.getHttpRequest(client.host, client.port, client.initMessage);
        ctx.channel().writeAndFlush(request);

        //send empty request
        //DefaultFullHttpRequest request = HttpRequestUtils.getEmptyHttpRequest(client.host, client.port);
        //ctx.channel().writeAndFlush(request);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse msg) throws Exception
    {
        System.out.println(msg.content().toString(CharsetUtil.UTF_8));

        channelHandlerContext.fireChannelRead(ReferenceCountUtil.retain(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}

