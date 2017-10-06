package _learn.jars.nettyInAction.demos.messageQueue.Socket.test;

import _learn.jars.nettyInAction.demos.messageQueue.Socket.tools.GetMessage;
import _learn.jars.nettyInAction.demos.messageQueue.Socket.tools.TransMessageToByteBuf;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Listing 2.4  of <i>Netty in Action</i>
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class Client
{
    public int port = 8099;
    public String host = "127.0.0.1";
    public String initMessage = "000";
    public Channel channel;
    EventLoopGroup group = new NioEventLoopGroup();

    public Client()
    {
    }

    public Client(int port)
    {
        this.port = port;
    }

    public Client(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void start(int connNum) throws Exception
    {
        ArrayList<Channel> channels = new ArrayList<>();
        Random random = new Random();
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0; i<connNum; i++)
        {
            start();
            channels.add(this.channel);
        }
        long StartTime = System.currentTimeMillis();
        while ((System.currentTimeMillis()) - StartTime < 1000*60)
        {
            pool.execute(new Task(channels.get(random.nextInt(connNum))));
        }
        pool.awaitTermination(10, TimeUnit.MINUTES);
    }

    static class Task implements Runnable
    {
        public Channel channel;

        public Task(Channel channel)
        {
            this.channel = channel;
        }

        @Override
        public void run()
        {
            String msg = GetMessage.getRandomMessage();
            this.channel.writeAndFlush(TransMessageToByteBuf.transToLengthField(msg));
        }
    }
    public void start() throws Exception
    {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception
                    {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
                        ch.pipeline().addLast(new IdleStateHandler(0, 0, 1, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new ClientInHandler(Client.this));
                    }
                });
        ChannelFuture cf = b.connect().sync();
        System.out.println("client connected server ");

        this.channel = cf.channel();

        cf.channel().closeFuture().sync();
        try
        {
        } finally
        {
            group.shutdownGracefully();
        }
    }
}

