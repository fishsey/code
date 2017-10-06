package _learn.javaSe._io._socket._BioTcp;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 4/24/17.
 */
public class test
{
    @Test
    public void test_()
    {
        int port = 6066;
        try
        {
            new Thread(new _Server(port)).start();
            TimeUnit.MILLISECONDS.sleep(10);

            new _Client(port).connectionToServer();
            new _Client(port).connectionToServer();
            new _Client(port).connectionToServer();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
