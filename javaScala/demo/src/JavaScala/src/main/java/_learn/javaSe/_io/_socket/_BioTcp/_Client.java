package _learn.javaSe._io._socket._BioTcp;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this is a class for class-client-socket
 * Created by fishsey on 2016/7/11.
 */
public class _Client
{
    static int port = 6066;
    static String serverName = "localhost";
    static AtomicInteger flag = new AtomicInteger(0);

    public _Client()
    {
        this(serverName, port);
    }

    public _Client(int port)
    {
        this(serverName, port);
    }

    public _Client(String serverName, int port)
    {
        this.serverName = serverName;
        this.port = port;
    }

    public void connectionToServer()
    {
        Socket client = null;
        try
        {
            System.out.println(flag.getAndIncrement() + " Connecting to server: " + serverName + " on port: " + port);

            //clint-side socket
            client = new Socket(serverName, port);
            System.out.println("Just connected to server: " + client.getRemoteSocketAddress());

            //Output Stream: send message to server-side
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("Hello from " + client.getLocalSocketAddress());

            //input stream: received message from server-side
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Server says " + in.readUTF());
        } catch (IOException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                client.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
