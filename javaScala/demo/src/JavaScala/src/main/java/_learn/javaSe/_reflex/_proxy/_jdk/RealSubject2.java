package _learn.javaSe._reflex._proxy._jdk;

/**
 * Created by fishsey on 2017/9/2.
 */
class RealSubject2 implements Subject2
{
    public void doSomething(String str)
    {
        System.out.println( "call doSomething(), " + str);
    }
}
