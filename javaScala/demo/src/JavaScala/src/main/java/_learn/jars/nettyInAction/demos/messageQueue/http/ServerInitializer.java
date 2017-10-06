package _learn.jars.nettyInAction.demos.messageQueue.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Listing 12.3 Initializing the ChannelPipeline
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class ServerInitializer extends ChannelInitializer<Channel>
{
    private final ChannelGroup group;

    public ServerInitializer(ChannelGroup group)
    {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        System.out.println("initChannel");

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ServerInHandler());
    }
}
