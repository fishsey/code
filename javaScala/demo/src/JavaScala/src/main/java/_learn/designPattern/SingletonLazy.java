package _learn.designPattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by fishsey on 2017/3/30.
 * 懒汉式单例（调用静态共有方法时创建实例，惰性求值）
 */
public class SingletonLazy
{
    private static SingletonLazy singleton;

    private SingletonLazy()
    {
    }

    private static class SingletonHelp
    {
        private static final SingletonLazy instance = new SingletonLazy();
    }

    //使用 sync 确保只创建一个实例对象
    public static synchronized SingletonLazy getInstance_1()
    {
        if (singleton == null)
        {
            singleton = new SingletonLazy();
        }
        return singleton;
    }


    //静态内部类单例模式:推荐使用的单例模式实现方式
    //第一次调用getInstance方法会导致虚拟机加载SingletonHelp类(class load only-one-time)
    //这种方式不仅确保线程安全，也能够保证单例对象的唯一性，同时也延迟了单例的实例化
    public static SingletonLazy getInstance_2()
    {
        return SingletonHelp.instance;
    }


    //Double Check Lock
    //singleton should be volatile
    public static SingletonLazy getInstance_3()
    {
        if (singleton == null)
        {
            synchronized (SingletonLazy.class)
            {
                if (singleton == null)
                {
                    singleton = new SingletonLazy();
                }
            }
        }
        return singleton;
    }


    public static void main(String args[])
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        //通过反射调用单例类的私有构造方法，创建多个实例对象
        Class c = Class.forName(SingletonLazy.class.getName());
        Constructor ct = c.getDeclaredConstructor();
        ct.setAccessible(true);
        SingletonLazy singleton = (SingletonLazy)ct.newInstance();
    }
}
