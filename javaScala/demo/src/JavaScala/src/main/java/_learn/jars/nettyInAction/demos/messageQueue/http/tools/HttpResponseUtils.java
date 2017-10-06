package _learn.jars.nettyInAction.demos.messageQueue.http.tools;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * Created by root on 8/14/17.
 */
public class HttpResponseUtils
{
    public static DefaultFullHttpResponse getHttpResponse(String message) throws URISyntaxException, UnsupportedEncodingException
    {
        DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(message.getBytes("UTF-8")));

        return res;
    }
}
