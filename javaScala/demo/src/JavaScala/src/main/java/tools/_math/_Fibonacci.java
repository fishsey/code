package tools._math;

import java.util.concurrent.RecursiveTask;

/**
 * Created by root on 5/23/17.
 */
public class _Fibonacci
{
    public static int getFibonacci(int n)
    {
        int f0 = 0;
        int f1 = 1;
        int temp;
        if (n == 0)
            return f0;
        else if (n == 1)
            return f1;
        else
        {
            for (int i=2; i<=n; i++)
            {
                temp = f1;
                f1 = f0 + f1;
                f0 = temp;
            }
            return  f1;
        }

    }

    static class _FibonacciFJ extends RecursiveTask<Integer>
    {
        final int n;

        _FibonacciFJ(int n)
        {
            this.n = n;
        }

        protected Integer compute()
        {
            if (n <= 1)
                return n;
            _FibonacciFJ f1 = new _FibonacciFJ(n - 1);
            f1.fork();
            _FibonacciFJ f2 = new _FibonacciFJ(n - 2);
            return f2.compute() + f1.join();
        }
    }
        public static void main(String args[])
        {
            System.out.println(_Fibonacci.getFibonacci(10));
            System.out.println(new _FibonacciFJ(10).invoke());
        }
}
