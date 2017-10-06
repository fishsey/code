package _learn.jars.nettyInAction.demos.messageQueue.Socket.tools;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by root on 8/14/17.
 */
public class TransMessageToByteBuf
{
    public static final int lengthField = 4;
    public static final ByteBuf header = Unpooled.buffer(lengthField);

    //msg must be less than 2^32 byte
    public static ByteBuf transToLengthField(String msg)
    {
        ByteBuf msgByte = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        return Unpooled.wrappedBuffer(header.copy().writeInt(msgByte.readableBytes()), msgByte);
    }
}
