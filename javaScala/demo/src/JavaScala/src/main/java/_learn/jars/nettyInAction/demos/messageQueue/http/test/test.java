package _learn.jars.nettyInAction.demos.messageQueue.http.test;

import _learn.jars.nettyInAction.demos.messageQueue.http.Server;
import org.junit.Test;

/**
 * Created by root on 8/14/17.
 */
public class test
{
    @Test
    public void test_server() throws InterruptedException
    {
        new Server().start();
    }

    @Test
    public void test_client() throws Exception
    {
        new Client().connect();
    }
}
