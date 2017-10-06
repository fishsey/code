package _learn.jars.nettyInAction.demos.messageQueue.http.tools;

import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by root on 8/13/17.
 */
public class HttpRequestUtils
{
    public static DefaultFullHttpRequest getHttpRequest(String host, int port, String initMessage) throws URISyntaxException, UnsupportedEncodingException
    {
        URI uri = new URI("http://" + host + ":" + port);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString(), Unpooled.wrappedBuffer(initMessage.getBytes("UTF-8")));

        request.headers().set(HttpHeaderNames.HOST.toString(), host);
        request.headers().set(HttpHeaderNames.CONNECTION.toString(), HttpHeaderValues.KEEP_ALIVE.toString());
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH.toString(), request.content().readableBytes());

        //request.headers().set(HttpHeaderNames.ACCEPT_ENCODING.toString(), HttpHeaderValues.GZIP.toString());

        return request;
    }

    public static DefaultFullHttpRequest getEmptyHttpRequest(String host, int port) throws URISyntaxException, UnsupportedEncodingException
    {
        URI uri = new URI("http://" + host + ":" + port);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString(), new EmptyByteBuf(new PooledByteBufAllocator()));

        request.headers().set(HttpHeaderNames.HOST.toString(), host);
        request.headers().set(HttpHeaderNames.CONNECTION.toString(), HttpHeaderValues.KEEP_ALIVE.toString());
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH.toString(), request.content().readableBytes());
        return request;
    }
}
