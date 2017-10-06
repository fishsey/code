package _learn.javaSe._concurParallel._base._container;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class _DelayQueue
{
    static int flag = 500;

    public static void main(String[] args) throws InterruptedException
    {
        Random rand = new Random();
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();

        // Fill with tasks that have random delays:
        for (int i = 0; i < 5; i++)
        {
            long delta = rand.nextInt(flag)  + System.currentTimeMillis();
            queue.put(new DelayedTask(delta));
        }
        System.out.println(queue);
        System.out.println(queue.size());

        //TimeUnit.SECONDS.sleep(1);//let all task delay
        //execute queue
        exec.execute(new DelayedTaskConsumer(queue));
        exec.shutdown();
    }

    static class DelayedTask implements Runnable, Delayed
    {
        private static AtomicLong counter = new AtomicLong();
        private final long id = counter.getAndIncrement();
        private final long delta;

        public DelayedTask(long delayInMilliseconds)
        {
            delta = delayInMilliseconds;
        }

        //设定到期时间，只有到期后才能被 take()
        //返回非正数则到期
        @Override
        public long getDelay(TimeUnit unit)
        {
            return this.delta - System.currentTimeMillis();
        }


        public void run()
        {
            System.out.println(this);
        }

        @Override
        public String toString()
        {
            return "DelayedTask{" +
                    "id=" + id +
                    ", delta=" + delta +
                    '}';
        }

        @Override
        public int compareTo(Delayed obj)
        {
            return ((Long)(this.id)).compareTo(((DelayedTask)obj).id);
            //return ((Long)this.delta).compareTo(((DelayedTask)obj).delta);
        }
    }


    static class DelayedTaskConsumer implements Runnable
    {
        private DelayQueue<DelayedTask> q;

        public DelayedTaskConsumer(DelayQueue<DelayedTask> q)
        {
            this.q = q;
        }

        public void run()
        {
            while (!q.isEmpty())
            {
                try
                {
                    q.take().run();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("Finished DelayedTaskConsumer");
        }
    }

}




