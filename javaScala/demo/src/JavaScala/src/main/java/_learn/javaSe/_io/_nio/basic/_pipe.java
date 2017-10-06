package _learn.javaSe._io._nio.basic;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Created by root on 5/15/17.
 */
public class _pipe
{
    public static void main(String args[]) throws IOException
    {
        Pipe pipe = Pipe.open();

        //向管道写数据
        Pipe.SinkChannel sinkChannel = pipe.sink();
        String newData = "New String to write to file..." + System.currentTimeMillis();
        System.out.println(newData);
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();//ready for read
        while (buf.hasRemaining())
        {
            sinkChannel.write(buf);
        }

        //从管道读取数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf = ByteBuffer.allocate(48);
        buf.clear();
        sourceChannel.read(buf);
        System.out.println("after write: " + buf);
        buf.flip();//ready for read
        System.out.println("after flip: " + buf);

        //buff -> byte[]
        byte[] dest = new byte[48];
        buf.get(dest, buf.position(), buf.limit());

        //output the byte[]
        System.out.println("after get: " + buf);
        for (byte b : dest)
            System.out.print((char) b);

    }
}
