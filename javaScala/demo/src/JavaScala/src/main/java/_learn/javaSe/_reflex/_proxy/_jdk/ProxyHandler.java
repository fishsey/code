package _learn.javaSe._reflex._proxy._jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fishsey on 2017/9/2.
 */
//所有对动态代理对象的方法调用都会被转向到 InvocationHandler 接口的实现上
public class ProxyHandler implements InvocationHandler
{
    private Object proxied;

    public ProxyHandler( Object proxied )
    {
        this.proxied = proxied;
    }

    public Object invoke(Object proxy, Method method, Object[] args ) throws Throwable
    {
        //do something: before
        System.out.println("before ... ");

        return method.invoke(this.proxied, args);

        //do something: after
        //return

    }
}
