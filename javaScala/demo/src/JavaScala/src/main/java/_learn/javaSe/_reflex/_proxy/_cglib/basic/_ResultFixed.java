package _learn.javaSe._reflex._proxy._cglib.basic;

import net.sf.cglib.proxy.FixedValue;

/**
 * 表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
 * @author zghw
 */
public class _ResultFixed implements FixedValue
{
    /**
     * 该类实现 FixedValue接口，同时锁定回调值为 999
     * (整型，CallbackFilter中定义的使用FixedValue型回调的方法为getConcreteMethodFixedValue，该方法返回值为整型)。
     */
    @Override
    public Object loadObject() throws Exception
    {
        System.out.println("锁定结果: 返回固定值（原方法不在执行）");
        Object obj = 999;
        return obj;
    }

}
