package _learn.javaSe._concurParallel._base._startUp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/*
* 异常不能跨线程补货，因此为线程设置异常处理类
* */
public class _UncaughtException
{
    // UncaughtException 异常处理类的实例对象
    public static Thread.UncaughtExceptionHandler MyUncaughtExceptionHandler =
            (thread, e) -> System.out.println("catch:\t" + thread + '\t' + e);

    //运行线程的实例对象：抛出异常
    public static Runnable ExceptionThread = () ->
    {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        throw new RuntimeException();
    };

    //线程工厂实例
    //线程工厂为创建的每个线程，添加同样的上下文操作
    public static ThreadFactory HandlerThreadFactory = (r) ->
    {
        Thread t = new Thread(r);
        System.out.println("created thread: " + t);

        //设置异常处理类
        t.setUncaughtExceptionHandler(MyUncaughtExceptionHandler);
        //System.out.println("setted UncaughtExceptionHandler: " + t.getUncaughtExceptionHandler());
        return t;
    };

    public static void main(String[] args)
    {
        /*
        * personality UncaughtExceptionHandler design for signal special thread
        * HandlerThreadFactory responsibility for create new thread
        * */
        ExecutorService exec = Executors.newCachedThreadPool(HandlerThreadFactory);
        //must be execute(), not submit()
        exec.execute(ExceptionThread);
        exec.shutdown();
    
        
        /*
        * default UncaughtExceptionHandler for all threads
        * */
        exec = Executors.newCachedThreadPool();
        exec.execute(ExceptionThread);
        exec.shutdown();


    }
}
