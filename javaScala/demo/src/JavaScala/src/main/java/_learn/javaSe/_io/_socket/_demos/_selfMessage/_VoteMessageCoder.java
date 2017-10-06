package _learn.javaSe._io._socket._demos._selfMessage;

/**
 * Created by root on 5/21/17.
 */

import java.io.IOException;

public interface _VoteMessageCoder
{
    //根据一个特定的协议，将投票消息转换成一个字节序列
    byte[] encoder(_VoteMessage msg) throws IOException;

    //根据相同的协议，对给定的字节序列进行解析，并根据信息的内容返回一个该消息类的实例。
    _VoteMessage decoder(byte[] input) throws IOException;
}  
