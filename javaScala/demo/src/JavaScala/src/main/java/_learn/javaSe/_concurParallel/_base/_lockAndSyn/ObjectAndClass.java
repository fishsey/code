package _learn.javaSe._concurParallel._base._lockAndSyn;

import java.util.concurrent.TimeUnit;

/**
 * Created by fishsey on 2017/6/9.
 */
public class ObjectAndClass
{
    public synchronized static void m1()
    {
        System.out.println("static m1");
        try
        {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized static void m2()
    {
        System.out.println("static m2");
        try
        {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized  void m3()
    {
        System.out.println("object m3");
        try
        {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized  void m4()
    {
        System.out.println("object m4");
        try
        {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        ObjectAndClass obj = new ObjectAndClass();
        new Thread(() -> ObjectAndClass.m1()).start();
        new Thread(() -> ObjectAndClass.m2()).start();
        new Thread(() -> obj.m3()).start();
        new Thread(() -> obj.m4()).start();
    }
}


