package _learn.javaSe._io._socket._BioTcp;

import java.io.DataInputStream;
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
public class _Server implements Runnable
{
    private ServerSocket serverSocket;
    private final int TIMEOUT = 1 << 12;

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
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
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
                TimeUnit.MILLISECONDS.sleep(100);

                //input stream: received message form client-side
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());

                //output stream: send message to client-side
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
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
