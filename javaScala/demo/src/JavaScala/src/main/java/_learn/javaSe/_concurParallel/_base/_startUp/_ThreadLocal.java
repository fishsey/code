package _learn.javaSe._concurParallel._base._startUp;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _ThreadLocal
{


    @Test
    public void test_()
    {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        Runnable r1 = new RunableTaskSet("1", threadLocal);
        Runnable r2 = new RunableTaskSet("2", threadLocal);
        Runnable r3 = new RunableTaskRead("3", threadLocal);
        Runnable r4 = new RunableTaskRead("4", threadLocal);

        ExecutorService es = Executors.newSingleThreadExecutor();

        es.execute(r1);
        es.execute(r3);//1

        es.execute(r2);
        es.execute(r3);//2

        es.shutdown();
    }
    class RunableTaskSet implements Runnable
    {
        public String ids;
        ThreadLocal<String> threadLocal;

        public RunableTaskSet(String ids, ThreadLocal<String> threadLocal)
        {
            this.ids = ids;
            this.threadLocal = threadLocal;
        }

        @Override
        public void run()
        {
            threadLocal.set(ids);
        }
    }

    class RunableTaskRead implements Runnable
    {
        public String ids;
        ThreadLocal<String> threadLocal;

        public RunableTaskRead(String ids, ThreadLocal<String> threadLocal)
        {
            this.ids = ids;
            this.threadLocal = threadLocal;
        }

        @Override
        public void run()
        {
            System.out.println(threadLocal.get());
        }
    }

}





