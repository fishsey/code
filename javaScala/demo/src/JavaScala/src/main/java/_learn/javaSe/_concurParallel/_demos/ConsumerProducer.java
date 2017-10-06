package _learn.javaSe._concurParallel._demos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerProducer
{
    private static Buffer buffer = new Buffer();
    
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new ProducerTask());
        executor.execute(new ConsumerTask());
        executor.shutdown();

    }
    
    // A task for adding an int to the buffer
    private static class ProducerTask implements Runnable
    {
        public void run()
        {
            try
            {
                int i = 1;
                while (true)
                {
                    System.out.println("Producer writes " + buffer.write(i++));
                    Thread.sleep((int) (Math.random() * 10000));
                }
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    // A task for reading and deleting an int from the buffer
    private static class ConsumerTask implements Runnable
    {
        public void run()
        {
            try
            {
                while (true)
                {
                    System.out.println("\t\t\tConsumer reads " + buffer.read());
                    Thread.sleep((int) (Math.random() * 10000));
                }
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private static class Buffer
    {
        private static final int CAPACITY = 1; // buffer totally size
        private java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>();
        
        //创建对象显式锁
        //基于显式锁的条件信号量
        private static Lock lock = new ReentrantLock();
        private static Condition notEmpty = lock.newCondition();
        private static Condition notFull = lock.newCondition();
        
        public int write(int value)
        {
            lock.lock(); // Acquire the lock
            try
            {
                while (queue.size() == CAPACITY)
                {
                    System.out.println("Wait for notFull condition");
                    notFull.await();//等待队列未满的信号
                }
                queue.add(value);
                notEmpty.signal(); //指示现在队列非空
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            } finally
            {
                lock.unlock();
                return value;
            }
        }
        
        public int read()
        {
            int value = 0;
            lock.lock();
            try
            {
                while (queue.isEmpty())
                {
                    System.out.println("\t\t\tWait for notEmpty condition");
                    notEmpty.await();//等待队列非空的信号
                }
                value = queue.remove();
                notFull.signal(); //指示现在队列未满
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            } finally
            {
                lock.unlock();
                return value;
            }
        }
    }
}
