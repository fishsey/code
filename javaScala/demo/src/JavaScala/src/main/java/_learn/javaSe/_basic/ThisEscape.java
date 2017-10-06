package _learn.javaSe._basic;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 4/6/17.
 */
public class ThisEscape
{
    final int number;

    public ThisEscape(int number, Temp temp) throws InterruptedException
    {
        //final逃逸， 未初始化
        doSomething();

        //this 逃逸到外部；
        temp.obj = this;
        System.out.println("----------------------------------");
        TimeUnit.SECONDS.sleep(3);

        //内部类，this逃逸
        new inner().doSomething(number);

        this.number = number;
    }

    public void doSomething()
    {
        System.out.println(this.number);
    }

    class inner
    {
        public void doSomething(int number)
        {
            if (ThisEscape.this.number == number)
                System.out.println("same");
            else
                System.out.println("not same");
        }
    }

    public static void main(String args[]) throws InterruptedException
    {
        Temp temp = new Temp();
        Thread t1 = new Thread(() ->
        {
            try
            {
                new ThisEscape(11, temp);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(temp.obj.number);
        t1.join();
        System.out.println(temp.obj.number);


    }
}

class Temp
{
    ThisEscape obj;
}
