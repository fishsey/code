package _learn.designPattern._constructPattern;

/**
 * Created by root on 4/27/17.
 */
public class BridgeMain
{
    public static void main(String[] args)
    {
        AbstractRoad speedWay = new SpeedWay();
        speedWay.aCar = new Car();
        speedWay.run();

        AbstractRoad street = new Street();
        street.aCar = new Bus();
        street.run();
    }
}

abstract class AbstractRoad
{
    AbstractCar aCar;
    void run() {};
}

abstract class AbstractCar
{
    void run() {};
}

class Street extends AbstractRoad
{
    @Override
    void run()
    {
        super.run();
        aCar.run();
        System.out.println("在市区街道行驶");
    }
}

class SpeedWay extends AbstractRoad
{
    @Override
    void run()
    {
        super.run();
        aCar.run();
        System.out.println("在高速公路行驶");
    }
}

class Car extends AbstractCar
{
    @Override
    void run()
    {
        super.run();
        System.out.print("小汽车");
    }
}

class Bus extends AbstractCar
{
    @Override
    void run()
    {
        super.run();
        System.out.print("公交车");
    }
}  
