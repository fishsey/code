package _learn.javaSe._concurParallel._base._control;//: concurrency/D1_CountDownLatchDemo.java

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _CountDownLatch
{
	//需要等待 5 个事件发生
    static final int SIZE = 5;
    
    public static void main(String[] args) throws Exception
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(SIZE);
		
        for (int i = 0; i < SIZE; i++)
            exec.execute(new WaitingTask(latch));
        
        for (int i = 0; i < SIZE; i++)
            exec.execute(new TaskPortion(latch));
        
        exec.shutdown(); // Quit when all tasks complete
    }

    // Performs some portion of a task:
    static class TaskPortion implements Runnable
    {
        private static int counter = 0;
        private final int id = counter++;
        private final CountDownLatch latch;

        TaskPortion(CountDownLatch latch)
        {
            this.latch = latch;
        }

        public void run()
        {
            try
            {
                doWork();
                latch.countDown();
            } catch (InterruptedException ex)
            {
            }
        }

        public void doWork() throws InterruptedException
        {
            System.out.println(id + " completed");
        }

    }


    // Waits on the CountDownLatch:
    static class WaitingTask implements Runnable
    {
        private static int counter = 0;
        private final int id = counter++;
        private final CountDownLatch latch;

        WaitingTask(CountDownLatch latch)
        {
            this.latch = latch;
        }

        public void run()
        {
            try
            {
                //等待，直到计算器为 0
                latch.await();
                System.out.println("Latch barrier passed for " + id);
            } catch (InterruptedException ex)
            {
                System.out.println(this + " interrupted");
            }
        }
    }

}

