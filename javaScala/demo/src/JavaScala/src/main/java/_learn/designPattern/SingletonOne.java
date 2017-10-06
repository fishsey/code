package _learn.designPattern;

/**
 * Created by fishsey on 2017/3/30.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 单例注册管理类
 */

public class SingletonOne
{
    protected SingletonOne()
    {
    }

    public static SingletonOne getInstance()
    {
        if (SingletonRegistryManager.isRegistred(SingletonOne.class))
        {
            return (SingletonOne) SingletonRegistryManager.getInstance(SingletonOne.class);
        }
        return new SingletonOne();
    }
}


abstract class SingletonRegistryManager
{
    private static Map<String, Object> singletonMap = new HashMap();
    public static Object getInstance(String className)
    {
        if (isRegistred(className))
        {
            return singletonMap.get(className);
        }
        return null;
    }
    public static Object getInstance(Class clazz)
    {
        return getInstance(clazz.getName());
    }


    public synchronized static void registry(String className)
    {
        if (!singletonMap.containsKey(className))
        {
            try
            {
                singletonMap.put(className, Class.forName(className).newInstance());
            } catch (InstantiationException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
    public synchronized static void registry(Class clazz)
    {
        registry(clazz.getName());
    }


    public synchronized static void unRegistry(String className)
    {
        if (singletonMap.containsKey(className))
        {
            singletonMap.remove(className);
        }
    }
    public synchronized static void unRegistry(Class clazz)
    {
        unRegistry(clazz.getName());
    }


    public synchronized static boolean isRegistred(String className)
    {
        return singletonMap.containsKey(className);
    }
    public synchronized static boolean isRegistred(Class clazz)
    {
        return isRegistred(clazz.getName());
    }

}


