package _learn.javaSe._io._nio.basic;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by root on 5/14/17.
 */
public class _Selector
{
    public static void main(String args[]) throws IOException
    {
        //Selector
        Selector selector = Selector.open();
        //Channel
        //与 Selector 一起使用时，Channel 必须处于非阻塞模式下
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        //注册
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        while (true)
        {
            int readyChannels = selector.select();
            if (readyChannels == 0)
                continue;
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext())
            {
                key = (SelectionKey) keyIterator.next();
                if (key.isAcceptable())
                {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable())
                {
                    // a connection was established with a remote server.
                } else if (key.isReadable())
                {
                    // a channel is ready for reading
                } else if (key.isWritable())
                {
                    // a channel is ready for writing
                }
                //Selector本身并不会移除 SelectionKey对象，当下次 channel处于就绪时，
                // Selector任然会把这些 key再次加入进来
                keyIterator.remove();
            }
        }

    }
}
