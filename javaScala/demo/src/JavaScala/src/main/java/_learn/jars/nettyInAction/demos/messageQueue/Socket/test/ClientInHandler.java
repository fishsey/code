package _learn.jars.nettyInAction.demos.messageQueue.Socket.test;

import _learn.jars.nettyInAction.demos.messageQueue.Socket.tools.TransMessageToByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Listing 2.3 of <i>Netty in Action</i>
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
@Sharable
public class ClientInHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    Client client;
    public AtomicInteger num = new AtomicInteger(0);
    public String HEARTBEAT_SEQUENCE = "HEARTBEAT";

    public ClientInHandler(Client client)
    {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
    {
        ctx.writeAndFlush(TransMessageToByteBuf.transToLengthField(client.initMessage));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws InterruptedException
    {
        System.out.println(ctx.channel().hashCode() + " receive:" + in.toString(CharsetUtil.UTF_8) + num.getAndIncrement());
        //TimeUnit.SECONDS.sleep(3);
        //String msg = GetMessage.getRandomMessage();
        //System.out.println("send:" + msg);
        //System.out.println();
        //ctx.channel().writeAndFlush(TransMessageToByteBuf.transToLengthField(msg));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            System.out.println("userEventTriggered " + num.getAndIncrement());
            ChannelFuture channelFuture = ctx.channel().writeAndFlush(TransMessageToByteBuf.transToLengthField(client.initMessage));
            channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }
}
