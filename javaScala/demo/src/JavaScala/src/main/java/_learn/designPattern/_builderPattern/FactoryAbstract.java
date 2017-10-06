package _learn.designPattern._builderPattern;

public class FactoryAbstract
{
    public static void main(String args[])
    {
        IFactory2 factory2 = new Factory2();
        //抽象工厂方法可以创建多个（接口）产品
        factory2.createProduct1().show();
        factory2.createProduct2().show();
    }
}

//抽象工厂
interface IFactory2
{
    //实现多个（接口）产品
    public IProduct1 createProduct1();
    public IProduct2 createProduct2();
}

//具体工厂
class Factory2 implements IFactory2
{
    public IProduct1 createProduct1()
    {
        return new Product1();
    }

    public IProduct2 createProduct2()
    {
        return new Product2();
    }
}


//抽象产品 1
interface IProduct1
{
    public void show();
}
//抽象产品 2
interface IProduct2
{
    public void show();
}

//具体产品 1
class Product1 implements IProduct1
{
    //产品方法
    public void show()
    {
        System.out.println("这是1型产品");
    }
}
//具体产品 2
class Product2 implements IProduct2
{
    public void show()
    {
        System.out.println("这是2型产品");
    }
}


