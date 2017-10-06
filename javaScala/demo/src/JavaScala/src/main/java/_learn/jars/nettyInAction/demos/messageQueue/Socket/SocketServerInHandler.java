package _learn.jars.nettyInAction.demos.messageQueue.Socket;

import _learn.jars.nettyInAction.demos.messageQueue.Socket.tools.TransMessageToByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Listing 12.1 HTTPRequestHandler
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class SocketServerInHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    public AtomicInteger num = new AtomicInteger(0);
    public static final long startTime = System.currentTimeMillis();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception
    {
        //System.out.println(msg.toString(CharsetUtil.UTF_8));
        ctx.channel().writeAndFlush(TransMessageToByteBuf.transToLengthField(msg.toString(CharsetUtil.UTF_8)));
        if (num.getAndIncrement() % 100 == 0)
        {
            System.out.println("server receive and send: "  + (num.get()-1) + "\t" + (System.currentTimeMillis()-startTime));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }

}
