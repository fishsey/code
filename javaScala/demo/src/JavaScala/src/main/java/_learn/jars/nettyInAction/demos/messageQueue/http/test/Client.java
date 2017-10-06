package _learn.jars.nettyInAction.demos.messageQueue.http.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

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
public class Client
{
    public int port = 8099;
    public String host = "127.0.0.1";
    public String initMessage = "000";
    public Channel channel;
    public EventLoopGroup workerGroup;

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
                        System.out.println("client initChannel . . . ");
                        ch.pipeline().addLast(new HttpClientCodec());

                        ch.pipeline().addLast(new clientOuthandler_1());
                        ch.pipeline().addLast(new clientOuthandler_2());

                        ch.pipeline().addLast(new HttpObjectAggregator(1 * 1024));
                        ch.pipeline().addLast(new IdleStateHandler(0, 0, 3, TimeUnit.SECONDS));

                        ch.pipeline().addLast(new ClientInHandlerSimple(Client.this));
                        ch.pipeline().addLast(new clientInhandler_1());
                        ch.pipeline().addLast(new clientInhandler_2());
                    }
                });

        b.option(ChannelOption.TCP_NODELAY, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 10);
        ChannelFuture f = b.connect(host, port).sync();

        System.out.println("client connected server ");
        this.channel = f.channel();
        this.workerGroup = workerGroup;

        f.channel().closeFuture().sync();
        try
        {
        } finally
        {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String args[]) throws Exception
    {
        new Client().connect();
    }
}
