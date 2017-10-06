package _learn.jars.nettyInAction.demos.messageQueue.http.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class clientOuthandler_2 extends ChannelOutboundHandlerAdapter
{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
    {
        System.out.println("clientOuthandler_2");
        ctx.writeAndFlush(msg);
    }

}
