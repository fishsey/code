package _learn.javaSe._concurParallel._base._startUp;

import java.util.concurrent.*;

/**
 * Created by root on 2/17/17.
 */
public class _CreateThread
{
    public static void main(String args[])
    {
        /*
         *1: new runnable().run()
        */
        Runnable r1 = new Way1_Runable("Runnable");
        r1.run();
        
        /*
         * 2-1: new Thread(runnable).start();
         * 2-2: new Thread(@overwrite run()).start();
         */
        Thread t1 = new Thread(new Way1_Runable("new thread(Runnable)"));
        t1.start();
        Thread t2 = new Thread( () -> System.out.println("inherit Thread interface"));
        t2.start();
    
        /*
        * 3: thread pool
        */
        ExecutorService executor = Executors.newCachedThreadPool();
        //ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.execute(new Way1_Runable("thread runnable - 1"));

        //Submit tasks to the executor: Future<T>
        executor.submit(new Way1_Runable("thread runnable - 2"));
        executor.submit(new Way2_Callable("thread callable - 3"));
        // Shut down the executor
        executor.shutdown();
        
        /*
        * 4: callable and future
        */
        FutureTask<String> ft = new FutureTask<>(new Way2_Callable("thread callable"));
        new Thread(ft).start();
        try
        {
            //callable() has return value
            System.out.println(ft.get());
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    static class Way1_Runable implements Runnable
    {
        String args = "init";
        public Way1_Runable(String args)
        {
            this.args = args;
        }

        @Override
        public void run()
        {
            System.out.println(args);
        }
    }

    static class Way2_Callable implements Callable<String>
    {
        String args = "init";


        public Way2_Callable(String args)
        {
            this.args = args;
        }

        @Override
        public String call() throws Exception
        {
            System.out.println(this.args);
            return this.args + "return value";
        }
        void test()
        {

        }
    }
}


