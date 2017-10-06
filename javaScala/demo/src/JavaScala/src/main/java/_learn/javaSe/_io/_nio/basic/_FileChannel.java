package _learn.javaSe._io._nio.basic;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by root on 5/14/17.
 */
public class _FileChannel
{
    /*
    * data flows from Channel to Buffer
    * */
    @Test
    public void FileChannelRead() throws IOException
    {
        //fileChannel
        RandomAccessFile aFile = new RandomAccessFile("tempData/wiki/input/wiki1k", "rw");
        FileChannel inChannel = aFile.getChannel();

        //buffer
        ByteBuffer buf = ByteBuffer.allocate(1024 * 10);

        //read from channel to buffer
        int bytesRead = inChannel.read(buf);

        //-1 means end of file
        while (bytesRead != -1)
        {
            System.out.println("--------------------------------------------" + bytesRead);

            //read and clear buffer
            //切换缓存的模式（写模式 -> 读模式）
            buf.flip();
            while (buf.hasRemaining())
            {
                //buf.get();//从缓存中读取数据（一个字节）
                System.out.print((char) buf.get());
            }
            buf.clear();
            //read again from channel to buffer
            bytesRead = inChannel.read(buf);
            System.out.println();
        }
        aFile.close();
    }

    /*
    * data flows from  Buffer to channel
    * */
    @Test
    public  void FileChannelWrite() throws IOException
    {
        //fileChannel
        RandomAccessFile aFile = new RandomAccessFile("tempData/temp", "rw");
        FileChannel inChannel = aFile.getChannel();

        //buffer container
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        buf.put(newData.getBytes());
        //turn buffer for write to read
        buf.flip();
        while (buf.hasRemaining())
        {
            int byteNums = inChannel.write(buf);
            System.out.println(byteNums);
        }
    }

    /*
     * data flows between Channel and Channel
     */
    @Test
    public  void FileChannelTransfer() throws IOException
    {
        //fromChannel
        RandomAccessFile fromFile = new RandomAccessFile("tempData/temp", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        //toChannel
        RandomAccessFile toFile = new RandomAccessFile("tempData/save", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

}
