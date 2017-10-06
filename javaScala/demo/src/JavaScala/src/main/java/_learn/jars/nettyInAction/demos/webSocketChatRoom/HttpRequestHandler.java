package _learn.jars.nettyInAction.demos.webSocketChatRoom;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
    private final String wsUri;
    private static final File INDEX = new File("/root/AAA/code/idea/demos/bookRecys/tempData/chatWebSocket.html");

    public HttpRequestHandler(String wsUri)
    {
        this.wsUri = wsUri;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception
    {
        if (request.uri().toLowerCase().endsWith(this.wsUri.toLowerCase()))
        {
            ctx.fireChannelRead(request.retain());
        } else
        {
            if (HttpUtil.is100ContinueExpected(request))
            {
                send100Continue(ctx);
            }

            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE.toString(), "text/html; charset=UTF-8");

            boolean keepAlive = HttpUtil.isKeepAlive(request);

            if (keepAlive)
            {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH.toString(), file.length());
                response.headers().set(HttpHeaderNames.CONNECTION.toString(), HttpHeaderValues.KEEP_ALIVE.toString());
            }
            ctx.write(response);

            if (ctx.pipeline().get(SslHandler.class) == null)
            {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else
            {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive)
            {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx)
    {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
