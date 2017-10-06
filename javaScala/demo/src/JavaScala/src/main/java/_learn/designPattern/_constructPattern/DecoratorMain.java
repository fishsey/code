package _learn.designPattern._constructPattern;

/**
 * Created by root on 5/2/17.
 */
public class DecoratorMain
{
    public static void main(String args[])
    {
        TheGreatestSage sage = new Monkey();
        TheGreatestSage bird = new Bird(sage);
        TheGreatestSage fish = new Fish(bird);
        sage.move();
        bird.move();
        fish.move();
    }
}

//component
interface TheGreatestSage
{
    public void move();
}
//concrete component
class Monkey implements TheGreatestSage
{
    @Override
    public void move()
    {
        System.out.println("Monkey Move");
    }
}

//decorator
class Change implements TheGreatestSage
{
    private TheGreatestSage sage;
    public Change(TheGreatestSage sage)
    {
        this.sage = sage;
    }
    @Override
    public void move()
    {
        sage.move();
    }
}
//concrete decorator
class Fish extends Change
{

    public Fish(TheGreatestSage sage)
    {
        super(sage);
    }

    @Override
    public void move()
    {
        System.out.println("Fish Move");
    }
}

class Bird extends Change
{
    public Bird(TheGreatestSage sage)
    {
        super(sage);
    }

    @Override
    public void move()
    {
        System.out.println("Bird Move");
    }
}
