package _learn.jars.nettyInAction.demos.messageQueue.http.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class clientInhandler_2 extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("clientInhandler_1 2");
        ReferenceCountUtil.release(msg);

    }
}
