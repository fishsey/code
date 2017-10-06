package _learn.javaSe._reflex._proxy._jdk;

import sun.misc.ProxyGenerator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by root on 2/18/17.
 */
public class test
{
    public static void main(String args[]) throws IOException
    {
        Subject2 real = new RealSubject2();

        //等价于执行 real.doSomething("hello word")
        Subject2 proxySubject = (Subject2) Proxy.newProxyInstance(Subject2.class.getClassLoader(),
                new Class[]{Subject2.class},
                new ProxyHandler(real));

        proxySubject.doSomething("hello word");
        System.out.println();

        //write to disk
        byte[] classByte = ProxyGenerator.generateProxyClass(proxySubject.getClass().toString(), new Class[]{proxySubject.getClass()});
        BufferedOutputStream cout = new BufferedOutputStream(new FileOutputStream("jdk.class"));
        cout.write(classByte);
        cout.flush();

        Method[] methods = Subject2.class.getMethods();
        for (int j = 0; j < methods.length; j++)
        {
            System.out.println(methods[j]);
        }

    }
    
}






