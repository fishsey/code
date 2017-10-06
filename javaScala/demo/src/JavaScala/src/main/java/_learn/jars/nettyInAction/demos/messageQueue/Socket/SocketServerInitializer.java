package _learn.jars.nettyInAction.demos.messageQueue.Socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Listing 12.3 Initializing the ChannelPipeline
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class SocketServerInitializer extends ChannelInitializer<SocketChannel>
{
    private final ChannelGroup group;

    public SocketServerInitializer(ChannelGroup group)
    {
        this.group = group;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        ch.pipeline().addLast(new SocketServerInHandler());
    }
}
