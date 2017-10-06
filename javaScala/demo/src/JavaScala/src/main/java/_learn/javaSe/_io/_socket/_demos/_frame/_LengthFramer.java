package _learn.javaSe._io._socket._demos._frame;

/**
 * Created by root on 5/21/17.
 */

import java.io.*;

public class _LengthFramer implements _Framer
{
    public static final int MAXMESSAGELENGTH = 65535;
    public static final int BYTEMASK = 0xff;
    public static final int SHORTMASK = 0xffff;
    public static final int BYTESHIFT = 8;

    private DataInputStream in;

    public _LengthFramer(InputStream in) throws IOException
    {
        this.in = new DataInputStream(in);    //数据来源
    }

    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException
    {
        //消息的长度不能超过65535
        if (message.length > MAXMESSAGELENGTH)
        {
            throw new IOException("message too long");
        }
        out.write((message.length >> BYTESHIFT) & BYTEMASK);
        out.write(message.length & BYTEMASK);
        out.write(message);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException
    {
        int length;
        try
        {
            //该方法读取 2个字节，将它们作为 big-endian整数进行解释，并以 int型整数返回它们的值
            length = in.readUnsignedShort();
        } catch (EOFException e)
        {
            // no (or 1 byte) message
            return null;
        }
        // 0 <= length <= 65535
        byte[] msg = new byte[length];
        //该方法处阻塞等待，直到接收到足够的字节来填满指定的数组
        in.readFully(msg);
        return msg;
    }
}  
