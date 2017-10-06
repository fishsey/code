package _learn.jars.nettyInAction.demos.webSocketChatRoom.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * @project: nettygame
 * @Title: ClientTest.java
 * @Package: cpgame.nettygame
 * @author: chenpeng
 * @email: 46731706@qq.com
 * @date: 2015年8月20日 下午1:45:24
 * @description:
 * @version:
 */
public class ClientTest
{
    String host = "127.0.0.1";
    int port = 8099;
    String initMessage = "000";

    public void connect() throws Exception
    {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception
                    {
                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new ClientInboundHandler(host, port, initMessage));

                        // 客户端发送的是 httprequest，所以要使用HttpRequestEncoder进行编码
                        ch.pipeline().addLast(new HttpRequestEncoder());

                    }
                });
        ChannelFuture f = b.connect(host, port).sync();
        b.option(ChannelOption.TCP_NODELAY, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

    }

    public static void main(String[] args) throws Exception
    {
        ClientTest client = new ClientTest();
        client.connect();
    }
}
