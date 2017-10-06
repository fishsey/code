package _learn.javaSe._io._socket._demos._frame;

/**
 * ${DESCRIPTION}
 * Created by root on 5/21/17.
 */

import java.io.IOException;
import java.io.OutputStream;

public interface _Framer
{
    //添加成帧信息并将指定消息输出到指定流
    void frameMsg(byte[] message, OutputStream out) throws IOException;

    //则扫描指定的流，从中抽取出下一条消息。
    byte[] nextMsg() throws IOException;
}  
