package tools._usage;

import sun.misc.ProxyGenerator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fishsey on 2017/9/22.
 */
public class ByteCodeGenerate
{
    public static void toDisk(Object proxySubject) throws IOException
    {
        //write to disk
        byte[] classByte = ProxyGenerator.generateProxyClass(proxySubject.getClass().toString(), new Class[]{proxySubject.getClass()});
        BufferedOutputStream cout = new BufferedOutputStream(new FileOutputStream("generic.class"));
        cout.write(classByte);
        cout.flush();
    }
}
