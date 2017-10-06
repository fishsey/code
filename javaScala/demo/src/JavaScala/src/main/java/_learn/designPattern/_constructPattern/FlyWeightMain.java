package _learn.designPattern._constructPattern;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 5/7/17.
 */
public class FlyWeightMain
{
    public static void main(String args[])
    {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight fly = factory.factory(new Character('a'));
        fly.operation("First Call");

        fly = factory.factory(new Character('b'));
        fly.operation("Second Call");

        fly = factory.factory(new Character('a'));
        fly.operation("Third Call");
    }
}

interface Flyweight
{
    //一个示意性方法，参数 state是外蕴状态
    public void operation(String state);
}

class ConcreteFlyweight implements Flyweight
{
    private Character intrinsicState = null;

    /**
     * 构造函数，内蕴状态作为参数传入
     * @param state
     */
    public ConcreteFlyweight(Character state)
    {
        this.intrinsicState = state;
    }


    /**
     * 外蕴状态作为参数传入方法中，改变方法的行为，
     * 但是并不改变对象的内蕴状态。
     */
    @Override
    public void operation(String state)
    {
        System.out.println("Intrinsic State = " + this.intrinsicState);
        System.out.println("Extrinsic State = " + state);
    }

}

class FlyweightFactory
{
    private Map<Character, Flyweight> files = new HashMap<Character, Flyweight>();

    public Flyweight factory(Character state)
    {
        //先从缓存中查找对象
        Flyweight fly = files.get(state);
        if (fly == null)
        {
            //如果对象不存在则创建一个新的Flyweight对象
            fly = new ConcreteFlyweight(state);
            //把这个新的Flyweight对象添加到缓存中
            files.put(state, fly);
        }
        return fly;
    }
}
