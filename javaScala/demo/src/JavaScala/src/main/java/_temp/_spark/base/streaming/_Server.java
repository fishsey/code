package _temp._spark.base.streaming;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * this is a class for server-socket
 * Created by fishsey on 2016/7/11.
 */
public class _Server
{
    private ServerSocket serverSocket;
    private final int TIMEOUT = 1000 * 60;

    public _Server(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(TIMEOUT);
    }

    public void run()
    {
        Socket clientSocket = null;
        while (true)
        {
            try
            {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "... ... .... ");

                //waiting for client-side connection
                clientSocket = serverSocket.accept();

                //create a thread handle client-side
                new Thread(new ServerThread(clientSocket)).start();

            } catch (SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    static class ServerThread implements Runnable
    {
        Socket server = null;
        public ServerThread(Socket server)
        {
            this.server = server;
        }
        @Override
        public void run()
        {
            try
            {
                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                while (true)
                {
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeUTF("Thank you for connecting fishsey");
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    server.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
