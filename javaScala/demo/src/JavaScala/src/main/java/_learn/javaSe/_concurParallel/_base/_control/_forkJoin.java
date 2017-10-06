package _learn.javaSe._concurParallel._base._control;

import java.util.concurrent.RecursiveTask;



/**
 * Created by root on 5/16/17.
 */
public class _forkJoin extends RecursiveTask<Long>
{
    int left, end;

    public _forkJoin(int left, int end)
    {
        this.left = left;
        this.end = end;
    }

    @Override
    protected Long compute()
    {
        if (left == end)
            return Long.valueOf(left);
        int mid = (left + end) >> 1;
        _forkJoin s1 = new _forkJoin(left, mid);
        _forkJoin s2 = new _forkJoin(mid + 1, end);
        s1.fork();
        s2.fork();
        return s2.join() + s1.join();
    }


    public static void main(String args[])
    {
        _forkJoin objTask = new _forkJoin(1, 100);
        System.out.println(objTask.compute());
    }
}
