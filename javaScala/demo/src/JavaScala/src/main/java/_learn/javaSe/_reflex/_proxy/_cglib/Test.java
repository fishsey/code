package _learn.javaSe._reflex._proxy._cglib;

import _learn.javaSe._reflex._proxy._cglib.basic.*;
import _learn.javaSe._reflex._proxy._cglib.lazyLoad.PropertyBean;
import _learn.javaSe._reflex._proxy._cglib.lazyLoad._LazyBean;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.io.IOException;

public class Test
{
    @org.junit.Test
    public void test_1() throws IOException
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new _Interceptor());

        enhancer.setSuperclass(TargetObject.class);
        TargetObject targetObject = (TargetObject)enhancer.create();

        targetObject.method1();
        System.out.println();

        //byte[] bytes = ProxyGenerator.generateProxyClass(targetObject.getClass().getName(), new Class[]{targetObject.getClass()});
        //BufferedOutputStream cout = new BufferedOutputStream(new FileOutputStream("cglib.class"));
        //cout.write(bytes);
        //cout.flush();

    }

    @org.junit.Test
    public void test_interface() throws IOException
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new _Interceptor());

        enhancer.setSuperclass(TargetInterface.class);
        TargetInterface targetObject = (TargetInterface)enhancer.create();

        targetObject.method1();
        System.out.println();

    }

    @org.junit.Test
    public void test_2()
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);

        /*
         * (1)callback1：方法拦截器
         * (2)NoOp.INSTANCE：这个 NoOp表示 no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
         * (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
         */
        Callback noopCb = NoOp.INSTANCE;
        Callback callback1 = new _Interceptor();
        Callback fixedValue = new _ResultFixed();
        Callback[] cbarray = new Callback[]{callback1, noopCb, fixedValue};
        enhancer.setCallbacks(cbarray);

        CallbackFilter callbackFilter = new _MethodCallbackFilter();
        enhancer.setCallbackFilter(callbackFilter);

        TargetObject targetObject = (TargetObject) enhancer.create();

        targetObject.method1();
        //targetObject.method2();
        //targetObject.method3();
    }


    @org.junit.Test
    public void test_3()
    {
        _LazyBean lazyBean = new _LazyBean("fishsey", 18);
        System.out.println("...........................");
        System.out.println();

        //未创建具体对象,o1 and o2 point same place in memory, but the object was un-change if create
        PropertyBean o1 = lazyBean.propertyBeanLazyLoader;
        PropertyBean o2 = lazyBean.propertyBeanDispatcher;
        System.out.println("........................... 1");
        o1.getKey();
        o2.getKey();
        System.out.println("........................... 2");
        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o1 == o2);
        System.out.println();

        //未创建具体对象, o1 and o2 point same place in memory, but the object was dynamic-change
        o1 = lazyBean.getPropertyBeanDispatcher();
        o2 = lazyBean.getPropertyBeanDispatcher();
        System.out.println("........................... getkey()");
        o1.getKey();
        o2.getKey();
        System.out.println("........................... sop");
        System.out.println(o1);
        System.out.println(o2);
        System.out.println("........................... == ");
        System.out.println(o1 == o2);

    }
}
