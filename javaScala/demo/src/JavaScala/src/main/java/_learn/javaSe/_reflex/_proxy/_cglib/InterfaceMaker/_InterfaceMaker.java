package _learn.javaSe._reflex._proxy._cglib.InterfaceMaker;

import _learn.javaSe._reflex._proxy._cglib.basic.TargetObject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * InterfaceMaker会动态生成一个接口，该接口包含指定类定义的所有方法。
* */
public class _InterfaceMaker
{
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        InterfaceMaker interfaceMaker = new InterfaceMaker();

        //抽取某个类的方法生成接口方法，并创建该接口类对象
        interfaceMaker.add(TargetObject.class);
        Class<?> targetInterface = interfaceMaker.create();
        for (Method method : targetInterface.getMethods())
        {
            System.out.println(method.getName());
        }

        //接口代理并设置代理接口方法拦截  
        Object object = Enhancer.create(Object.class, new Class[]{targetInterface}, new MethodInterceptor()
        {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable
            {
                System.out.println("调用前");

                Object result = null;
                try
                {
                    result = methodProxy.invokeSuper(obj, args);
                } catch (Throwable throwable)
                {
                    System.out.println(method.getName());
                }

                System.out.println("调用后");
                return result;
            }
        });

        Method targetMethod1 = object.getClass().getMethod("method1");
        targetMethod1.invoke(object);
        System.out.println("....................................");

        Method targetMethod3 = object.getClass().getMethod("method3");
        targetMethod3.invoke(object);
    }

}
