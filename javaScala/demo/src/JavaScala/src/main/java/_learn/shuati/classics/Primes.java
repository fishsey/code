package _learn.shuati.classics;

import org.junit.Test;

/**
 * Created by fishsey on 2017/9/20.
 */
public class Primes
{
    @Test
    public void test_1()
    {
        int x = 100;
        boolean isprime = true;
        for (int i = 1; i <= x; i++)
        {
            if (i == 1 || (i & 1) == 0 && i != 2)//1, 偶数（除 2） 均不是素数
                continue;

            //只对奇数进行判断
            for (int j = 3; j <= Math.sqrt(i); j += 2)
            {
                if (i % j == 0)
                {
                    isprime = false;
                    break;
                }
            }

            if (isprime)
                System.out.print(i + " ");
            isprime = true;
        }
    }

    @Test
    public void test_2()
    {
        int x = 100;
        int[] prime = new int[100];
        prime[0] = 2;
        int count = 1;//当前偶数的个数

        //如果 x 不是素数， 则 x 必然可以被 <x 的素数整除
        boolean isprime = true;
        for (int i = 3; i <= x; i += 2)
        {
            for (int j = 0; j < count; j++)
            {
                if (i % prime[j] == 0)
                {
                    isprime = false;
                    break;
                }
            }

            if (isprime)
                prime[count++] = i;

            //reset mark
            isprime = true;
        }

        for (int k = 0; k < count; k++)
        {
            System.out.print(prime[k] + " ");
        }
    }
}
