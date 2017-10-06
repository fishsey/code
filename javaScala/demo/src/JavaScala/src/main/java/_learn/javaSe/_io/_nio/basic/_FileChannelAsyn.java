package _learn.javaSe._io._nio.basic;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 5/16/17.
 */
public class _FileChannelAsyn
{
    @Test
    public  void _readBlocking()
    {
        Path path = Paths.get("tempData/wiki/input/wiki1k");
        AsynchronousFileChannel fileChannel = null;
        try
        {
            fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            long position = 0;

            Future<Integer> operation = fileChannel.read(buffer, position);

            while (!operation.isDone()) ;//Syn

            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            System.out.println(new String(data));
            buffer.clear();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void _readAsyn() throws IOException, InterruptedException
    {
        Path path = Paths.get("tempData/temp11");
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        long position = 0;
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        
        
        //attachment and dest-bytebuff must be the same-one
        fileChannel.read(buffer1, position, buffer1, new CompletionHandler<Integer, ByteBuffer>()
        {
            @Override
            public void completed(Integer result, ByteBuffer attachment)
            {
                System.out.println("result = " + result);
                System.out.println(attachment);
                attachment.flip();

                //process data in bytebuff
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);

                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment)
            {
                System.out.println("failed");
            }
        });
    
        System.out.println("hello");

        TimeUnit.SECONDS.sleep(3);
    }


    public static void _writeAsyn() throws IOException, InterruptedException
    {
        Path path = Paths.get("tempData/testWrite.txt");
        if (!Files.exists(path))
        {
            Files.createFile(path);
        }
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        buffer.put("test data".getBytes());
        buffer.flip();

        fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>()
        {
            @Override
            public void completed(Integer result, ByteBuffer attachment)
            {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment)
            {
                System.out.println("Write failed");
                exc.printStackTrace();
            }
        });

        //waiting completed
        TimeUnit.SECONDS.sleep(3);

    }



}
