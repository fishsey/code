package _learn.designPattern._builderPattern;


/**
 * Created by fishsey on 2017/3/30.
 * 工厂方法模式
 */
public class FactoryMethod
{
    public static void main(String[] args)
    {
        //使用工厂方法后，调用端的耦合度大大降低了
        //并且对于工厂来说，是可以扩展的(工厂可以随时改变自己的实现，而不影响调用端的使用)
        IFactory factory = new Factory();
        //具体工厂产生一个具体的产品
        Car car = factory.createCar();
        //具体产品的方法
        car.show();
    }
}

//抽象工厂
interface IFactory
{
    public Car createCar();
}

//具体工厂
class Factory implements IFactory
{
    public Car createCar()
    {
        Engine engine = new Engine();
        Underpan underpan = new Underpan();
        Wheel wheel = new Wheel();
        Car car = new Car(engine, underpan, wheel);
        return car;
    }
}

//抽象产品
interface ICar
{
    public void show();
}

//具体产品
class Car implements ICar
{
    Engine engine;
    Underpan underpan;
    Wheel wheel;

    public Car(Engine engine, Underpan underpan, Wheel wheel)
    {
        this.engine = engine;
        this.underpan = underpan;
        this.wheel = wheel;
    }


    public void show()
    {
        System.out.println(toString());
    }

    @Override
    public String toString()
    {
        return "Car{" +
                "engine=" + engine +
                ", underpan=" + underpan +
                ", wheel=" + wheel +
                '}';
    }
}

class Engine
{
    @Override
    public String toString()
    {
        return "Engine{\"这是汽车的发动机\"}";
    }
}

class Underpan
{
    @Override
    public String toString()
    {
        return "Underpan{\"这是汽车的底盘\"}";
    }
}

class Wheel
{
    @Override
    public String toString()
    {
        return "Wheel{\"这是汽车的轮胎\"}";
    }
}
