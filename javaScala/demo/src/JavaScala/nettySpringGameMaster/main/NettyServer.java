package _temp._netty._nettySpringGameMaster.main;

import _temp._netty._nettySpringGameMaster.main.domain.ERequestType;
import _temp._netty._nettySpringGameMaster.main.netty.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServer
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected static final int parentGroupSize = 4;

    protected static final int childGroupSize = 4;

    private static final EventLoopGroup parentGroup = new NioEventLoopGroup(parentGroupSize);
    private static final EventLoopGroup childGroup = new NioEventLoopGroup(childGroupSize);

    private ServerInitializer initializer;
    private final int port;

    public NettyServer(int port)
    {
        this.port = port;
    }

    public void setInitializer(ServerInitializer initializer)
    {
        this.initializer = initializer;
    }

    public void run() throws Exception
    {
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(this.initializer);

            Channel ch = null;
            this.logger.info(ERequestType.parse(this.initializer.getRequestType()).getValue()
                    + " server started at port " + this.port + '.');

            if (ERequestType.HTTP.equals(ERequestType.parse(this.initializer.getRequestType())))
            {
                ch = b.bind(this.port).sync().channel();
            } else
            {
                ch = b.bind(new InetSocketAddress(this.port)).sync().channel();
            }

            ch.closeFuture().sync();
        } finally
        {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
