package _learn.jars.nettyInAction._util;

import io.netty.buffer.ByteBuf;

/**
 * Created by fishsey on 2017/6/25.
 */
public class ByteBufOutput
{
    public static void output(ByteBuf read)
    {
        while (read.isReadable())
            System.out.print(((char) read.readByte()));
        System.out.println();
    }

    public static String toString(ByteBuf read)
    {
        StringBuffer sb = new StringBuffer();
        while (read.isReadable())
            sb.append((char) read.readByte());
        return sb.toString();
    }


}
