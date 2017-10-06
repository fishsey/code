package _learn.jars.nettyInAction.demos.messageQueue.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * Listing 12.4 Bootstrapping the server
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class Server
{
    private Channel channel;
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    public int port = 8099;
    protected static final int parentGroupSize = 1;
    protected static final int childGroupSize = 4;
    private static final EventLoopGroup parentGroup = new NioEventLoopGroup(parentGroupSize);
    private static final EventLoopGroup childGroup = new NioEventLoopGroup(childGroupSize);

    public void start() throws InterruptedException
    {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(createInitializer(channelGroup));

        ChannelFuture future = bootstrap.bind().sync();
        System.out.println("bind ready for connected . . . ");

        channel = future.channel();

        future.channel().closeFuture().sync();

        try
        {

        }finally
        {
            destroy();
        }
    }

    protected ServerInitializer createInitializer(ChannelGroup group)
    {
        return  new ServerInitializer(group);
    }

    public void destroy()
    {
        if (channel != null)
        {
            channel.close();
        }
        channelGroup.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
