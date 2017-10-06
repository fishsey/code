package _learn.javaSe._reflex._proxy;

/**
 * Created by root on 5/8/17.
 */
public class _StaticProxy
{
    public static void main(String args[])
    {
        RealSubject subject = new RealSubject();
        Proxy p = new Proxy(subject);
        p.request();
    }
}

interface Subject
{
    void request();
}

class RealSubject implements Subject
{
    public void request()
    {
        System.out.println("RealSubject");
    }
}

class Proxy implements Subject
{
    private Subject subject;

    public Proxy(Subject subject)
    {
        this.subject = subject;
    }

    public void request()
    {
        System.out.println("begin");
        subject.request();
        System.out.println("end");
    }
}
