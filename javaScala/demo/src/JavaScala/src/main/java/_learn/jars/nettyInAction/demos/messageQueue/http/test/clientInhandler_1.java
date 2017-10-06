package _learn.jars.nettyInAction.demos.messageQueue.http.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class clientInhandler_1 extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("clientInhandler_1 1");
        ctx.fireChannelRead(msg);

    }
}
