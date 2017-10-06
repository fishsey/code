package tools.usage;

import java.io.*;
import java.net.MalformedURLException;

/**
 * Created by fishsey on 2017/3/20.
 */
public class TransToByteArray
{

    public static byte[] loadClass(String path)
    {
        byte[] classData = null;
        try
        {
            InputStream input = new BufferedInputStream(new FileInputStream(path));
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();
            while (data != -1)
            {
                buffer.write(data);
                data = input.read();
            }
            input.close();
            classData = buffer.toByteArray();

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return classData;
    }
}


