package _learn.jars.nettyInAction.demos.webSocketChatRoom.test;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.net.URI;

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
public class ClientInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>
{
    String host;
    int port;
    String initMessage;
    int id = 0;

    public ClientInboundHandler(String host, int port, String initMessage)
    {
        this.host = host;
        this.port = port;
        this.initMessage = initMessage;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        URI uri = new URI("http://" + host + ":" + port + "/ws");

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString(), Unpooled.wrappedBuffer(
                new StringBuffer().append(id).append(",").append(initMessage).toString().getBytes("UTF-8")));

        // 构建 http请求
        request.headers().set(HttpHeaderNames.HOST.toString(), host);
        request.headers().set(HttpHeaderNames.CONNECTION.toString(), HttpHeaderValues.KEEP_ALIVE.toString());
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH.toString(), request.content().readableBytes());

        System.out.println(" channelActive send http request to server . . .   . . .   . . .   ");

        // 发送http请求
        ctx.channel().write(request);
        ctx.channel().flush();
        ctx.channel().closeFuture().sync();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        System.out.println("get message");
        if (msg instanceof HttpResponse)
        {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }


        if (msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }

        if (msg instanceof ByteBuf)
        {
            ByteBuf messageData = (ByteBuf) msg;
            int commandId = messageData.readInt();
            int length = messageData.readInt();
            byte[] c = new byte[length];
            messageData.readBytes(c);
            System.out.println("commandId:" + commandId + "\tmessage:" + new String(c));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception
    {
        System.out.println(msg.content().toString(CharsetUtil.UTF_8));
    }
}

