package _learn.jars.nettyInAction.demos.messageQueue.http;

import _learn.jars.nettyInAction.demos.messageQueue.http.tools.HttpResponseUtils;
import _learn.jars.nettyInAction.demos.messageQueue.http.tools.HttpUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Listing 12.1 HTTPRequestHandler
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class ServerInHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
    public AtomicInteger num = new AtomicInteger(0);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception
    {
        System.out.println(request.content().toString(CharsetUtil.UTF_8));
        if (request.content().readableBytes() == 0)
        {
            System.out.println("................ end ");
            ctx.channel().closeFuture().sync();
        }
        //write message back
        String content = "back up : from server " + num.getAndIncrement();
        DefaultFullHttpResponse res = HttpResponseUtils.getHttpResponse(content);
        HttpUtil.setContentLength(res, Unpooled.wrappedBuffer(content.getBytes("UTF-8")).readableBytes());
        HttpUtils.sendHttpResponse(ctx, request, res);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }

}
