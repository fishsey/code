package _learn.javaSe._concurParallel._demos;


import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 1/16/17.
 */
public class Philosopher extends Thread
{
    public static void main(String args[]) throws InterruptedException
    {
        Philosopher[] philosophers = new Philosopher[5];
        ReentrantLock table = new ReentrantLock();

        for (int i = 0; i < 5; ++i)
            philosophers[i] = new Philosopher(table);

        for (int i = 0; i < 5; ++i)
        {
            philosophers[i].setLeft(philosophers[(i + 4) % 5]);
            philosophers[i].setRight(philosophers[(i + 1) % 5]);
            philosophers[i].start();
        }
        for (int i = 0; i < 5; ++i)
            philosophers[i].join();
    }

    boolean eating;
    Philosopher left, right;
    ReentrantLock tableLock;
    Condition condition;
    Random random;
    int thinkCount;

    public Philosopher(ReentrantLock tableLock)
    {
        this.eating = false;
        this.tableLock = tableLock;
        this.condition = this.tableLock.newCondition();
        this.random = new Random();
    }


    public void setLeft(Philosopher left)
    {
        this.left = left;
    }

    public void setRight(Philosopher right)
    {
        this.right = right;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                think();//depends on conditions, if or not execute
                eat();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void eat() throws InterruptedException
    {
        this.tableLock.lock();

        try
        {
            while (this.left.eating || this.right.eating)
                this.condition.await();//wait the signal for wake up
            this.eating = true;
        } finally
        {
            this.tableLock.unlock();
        }

        Thread.sleep(1000);
    }

    private void think() throws InterruptedException
    {
        this.tableLock.lock();//lock the table, in order to keep all status

        try
        {
            this.eating = false;//updating status of self
            this.left.condition.signal();//wake up the waiting thread
            this.right.condition.signal();
        } finally
        {
            this.tableLock.unlock();
        }

        ++thinkCount;
        if (thinkCount % 10 == 0)
            System.out.println("Philosopher " + this + " has thought " + thinkCount + " times");
        Thread.sleep(1000);
    }


}
