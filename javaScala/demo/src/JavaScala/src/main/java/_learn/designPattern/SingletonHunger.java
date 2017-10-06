package _learn.designPattern;

/**
 * 饿汉式单例（类加载时创建实例）
 */
public class SingletonHunger
{
    private static SingletonHunger singleton = new SingletonHunger();

    private SingletonHunger()
    {
    }

    public static SingletonHunger getInstance()
    {
        return singleton;
    }

}
