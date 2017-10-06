package _learn.javaSe._concurParallel._base._control;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class _Exchanger
{
    public static void main(String[] args) throws Exception
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Integer>> xc = new Exchanger<List<Integer>>();

        List<Integer> producerList = new CopyOnWriteArrayList<Integer>();
        List<Integer> consumerList = new CopyOnWriteArrayList<Integer>();

        exec.execute(new ExchangerProducer<Integer>(xc, producerList));
        exec.execute(new ExchangerConsumer<Integer>(xc, consumerList));

        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }


    static class ExchangerProducer<T> implements Runnable
    {
        private Exchanger<List<T>> exchanger;
        private List<T> holder;
        private static Random random = new Random();
        private static final int FULLNUM = 10;

        ExchangerProducer(Exchanger<List<T>> ex, List<T> holder)
        {
            this.exchanger = ex;
            this.holder = holder;
        }

        public void run()
        {
            try
            {
                while (!Thread.interrupted())
                {
                    for (int i = 0; i < FULLNUM; i++)
                        holder.add((T) (Integer)random.nextInt());

                    System.out.println("ExchangerProducer " + holder);//output before exchange

                    //waiting for exchange with ExchangerConsumer
                    holder = exchanger.exchange(holder);

                    System.out.println("ExchangerProducer " + holder);//output after exchange

                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e)
            {
            }
        }
    }

    static class ExchangerConsumer<T> implements Runnable
    {
        private Exchanger<List<T>> exchanger;
        private List<T> holder;
        private volatile T value;

        ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder)
        {
            exchanger = ex;
            this.holder = holder;
        }

        public void run()
        {
            try
            {
                while (!Thread.interrupted())
                {
                    //exchange with ExchangerProducer
                    holder = exchanger.exchange(holder);

                    System.out.println("ExchangerConsumer " + holder);
                    for (T x : holder)
                    {
                        holder.remove(x); // OK for CopyOnWriteArrayList
                    }

                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e)
            {
                // OK to terminate this way.
            }
        }
    }
}


