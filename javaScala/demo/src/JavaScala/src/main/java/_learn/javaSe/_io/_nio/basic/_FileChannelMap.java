package _learn.javaSe._io._nio.basic;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by fishsey on 2017/6/24.
 */
public class _FileChannelMap
{

    @Test
    public void test() throws IOException
    {
        RandomAccessFile aFile = new RandomAccessFile("tempData/temp11", "rw");
        FileChannel inChannel = aFile.getChannel();
        Boolean flag = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, 100).isDirect();
        System.out.println(flag);
    }
}
