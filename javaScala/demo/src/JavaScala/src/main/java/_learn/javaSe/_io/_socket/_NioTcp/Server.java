package _learn.javaSe._io._socket._NioTcp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;


public class Server
{
    //缓冲区的长度  
    private static final int BUFSIZE = 256;

    //select方法等待信道准备好的最长时间  
    private static final int TIMEOUT = 1000;

    public static void main(String[] args) throws IOException
    {
        //创建一个选择器
        Selector selector = Selector.open();

        //实例化一个信道: ServerSocketChannel
        ServerSocketChannel listnChannel = ServerSocketChannel.open();

        //listnChannel.socket().bind(new InetSocketAddress(1025));
        listnChannel.bind(new InetSocketAddress(1025));
        listnChannel.configureBlocking(false);
        listnChannel.register(selector, SelectionKey.OP_ACCEPT);

        //创建一个实现了协议接口的对象
        iHandler protocol = new Handler(BUFSIZE);

        //不断轮询 select方法，获取准备好的信道所关联的 Key集
        while (true)
        {
            //一直等待,直至有信道准备好了I/O操作  
            if (selector.select(TIMEOUT) == 0)
            {
                //在等待信道准备的同时，也可以异步地执行其他任务，  
                //这里只是简单地打印"."  
                System.out.print(".server ");
                continue;
            }

            //获取准备好的信道所关联的Key集合的iterator实例  
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            System.out.println("\nkey..........................................");
            //循环取得集合中的每个键值
            while (keyIter.hasNext())
            {
                SelectionKey key = keyIter.next();
                //如果服务端信道感兴趣的I/O操作为accept  
                if (key.isAcceptable())
                {
                    protocol.handleAccept(key);
                }

                //如果客户端信道感兴趣的 I/O操作为 read
                if (key.isReadable())
                {
                    protocol.handleRead(key);
                }

                //如果该键值有效，并且其对应的客户端信道感兴趣的I/O操作为 write
                if (key.isValid() && key.isWritable())
                {
                    protocol.handleWrite(key);
                }

                //这里需要手动从键集中移除当前的key  
                keyIter.remove();
            }
        }
    }
}  
