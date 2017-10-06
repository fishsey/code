package _learn.designPattern._actionsPattern;

import java.util.Vector;

/**
 * Created by root on 4/17/17.
 */
public class ObserverMain
{
    public static void main(String[] args)
    {
        //an concrete observered object
        Subject sub = new ConcreteSubject();
        sub.addObserver(new ConcreteObserver1()); //添加观察者1
        sub.addObserver(new ConcreteObserver2()); //添加观察者2
        sub.doSomething();
    }
}


abstract class Subject
{
    private Vector<Observer> obs = new Vector();
    
    public void addObserver(Observer obs)
    {
        this.obs.add(obs);
    }
    
    public void delObserver(Observer obs)
    {
        this.obs.remove(obs);
    }
    
    protected void notifyObserver()
    {
        for (Observer o : obs)
        {
            o.update();
        }
    }
    
    //something happened and notify all observer
    public abstract void doSomething();
}

class ConcreteSubject extends Subject
{
    public void doSomething()
    {
        System.out.println("被观察者事件反生");
        this.notifyObserver();
    }
}

interface Observer
{
    public void update();
}

class ConcreteObserver1 implements Observer
{
    public void update()
    {
        System.out.println("观察者1收到信息，并进行处理。");
    }
}

class ConcreteObserver2 implements Observer
{
    public void update()
    {
        System.out.println("观察者2收到信息，并进行处理。");
    }
}
