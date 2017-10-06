package _learn.javaSe._concurParallel._base._lockAndSyn;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * Created by fishsey on 2017/4/5.
 */
public class SpinLock
{

    public static void main(String[] args) throws InterruptedException
    {
        SpinLock lock = new SpinLock();
        for (int i = 0; i < 100; i++)
        {
            new Thread(new Test(lock)).start();
        }

        Thread.currentThread().sleep(1000);
        System.out.println(Test.sum);
    }


    //CAS 持有自旋锁的线程对象
    //init value is null
    AtomicReference<Thread> owner = new AtomicReference<Thread>();

    /*
    * lock函数将owner设置为当前线程，并且预测原来的值为空。
    * */
    public void lock()
    {
        Thread cur = Thread.currentThread();
        //一直被执行，直至第一个线程调用 unlock函数将 owner设置为 null，第二个线程才能进入临界区
        //for first thread owner is null
        while (!owner.compareAndSet(null, cur)) ;
    }

    /*
    * unlock函数将owner设置为null，并且预测值为当前线程。当有第二个线程调用lock操作时由于owner值不为空，导致循环
    * */
    public void unLock()
    {
        Thread cur = Thread.currentThread();
        owner.compareAndSet(cur, null);
    }

    /*
    * 创建实例对象将类变量 +1
    * */
    public static class Test implements Runnable
    {
        static int sum;
        private SpinLock lock;

        public Test(SpinLock lock)
        {
            this.lock = lock;
        }

        @Override
        public void run()
        {
            this.lock.lock();
            sum++;
            this.lock.unLock();
        }
    }
}
