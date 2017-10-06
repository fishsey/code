package _learn.javaSe._reflex._proxy._cglib.basic;


/**
 * 没有实现接口，需要 CGlib 动态代理的目标类
 */
public class TargetObject
{
    public void method1()
    {
        System.out.println("method1");
    }

    public void method2()
    {
        System.out.println("method2");
    }

    public void method3()
    {
        System.out.println("method3");
    }

    @Override
    public String toString()
    {
        return "TargetObject []" + getClass();
    }
}
