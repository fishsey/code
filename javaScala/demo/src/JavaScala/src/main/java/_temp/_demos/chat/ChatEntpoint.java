package _temp._demos.chat;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/websocket/chatRom")
public class ChatEntpoint
{
    private static final String GUEST_PREFIX = "visitGuest: ";
    private static final Set<ChatEntpoint> clientSet = new CopyOnWriteArraySet<>();
    //a set contains all client-side of on-line
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private final String nickname;
    private Session session;

    public ChatEntpoint()
    {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }

    @OnOpen
    public void start(Session session)
    {
        this.session = session;
        clientSet.add(this);
        String message = String.format("[ %s, %s ]", nickname, "welcome to the chatRoom");
        broadcast(message);
    }

    @OnClose
    public void end()
    {
        clientSet.remove(this);
        String message = String.format("[ %s, %s�]", nickname, "leave the chatRoom");
        broadcast(message);
    }

    @OnMessage
    public void incoming(String message)
    {
        String filteredMessage = String.format("%s: %s", nickname, filter(message));
        broadcast(filteredMessage);
    }

    @OnError
    public void onError(Throwable t) throws Throwable
    {
        System.out.println("WebSocket encounter error:\n" + t);
    }

    /*
    * server-side broadcast message to all client-side
    * */
    private static void broadcast(String msg)
    {
        for (ChatEntpoint client : clientSet)
        {
            try
            {
                synchronized (client)
                {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e)
            {
                System.out.println("error encounter: " + client + "can not send message");
                clientSet.remove(client);
                try
                {
                    client.session.close();
                } catch (IOException e1)
                {
                }
                String message = String.format("[ %s, %s�]", client.nickname, "disconnected");
                broadcast(message);
            }
        }
    }

    private static String filter(String message)
    {
        if (message == null)
            return null;
        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++)
        {
            switch (content[i])
            {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        return (result.toString());
    }
}
