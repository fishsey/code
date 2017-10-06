package _learn.designPattern._constructPattern;

/**
 * Created by root on 4/27/17.
 */
public class AdapterMain
{
    public static void main(String args[])
    {
        Target cls = new Adapter();
        cls.request();

    }
}

// 定义客户端期待的接口
interface Target
{
    void request();
}

//定义需要适配的类: 与客户端期待的接口不符合
 class Adaptee
{
    public void SpecificRequest()
    {
        System.out.println("This is a special request.");
    }
}

// 定义适配器
class Adapter implements Target
{
    private Adaptee adaptee = new Adaptee();

    // 通过重写，表面上调用Request()方法，变成了实际调用SpecificRequest()
    @Override
    public void request()
    {
        adaptee.SpecificRequest();
    }

}
