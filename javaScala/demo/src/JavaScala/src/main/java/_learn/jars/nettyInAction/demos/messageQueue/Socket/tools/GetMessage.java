package _learn.jars.nettyInAction.demos.messageQueue.Socket.tools;

import java.util.Random;

/**
 * Created by root on 8/15/17.
 */
public class GetMessage
{
    public static Random random = new Random();
    public static StringBuffer sb = new StringBuffer();

    public static String getRandomMessage()
    {
        int length = random.nextInt(10);
        if (sb.length() > length)
            return sb.subSequence(0, length).toString();
        else
        {
            while (sb.length() < length)
                sb.append("abcdefhij");
        }

        return sb.subSequence(0, length).toString();
    }
}
