package tools._datastructure._list._util;

import org.junit.Test;

/**
 * Created by root on 2/13/17.
 */
public class MaxSubSum
{

    @Test
    public void test_()
    {
         int[] array = {-23, 17, -7, 11, -2, 1, -34};
        System.out.println(maxSubSum(array)[0]);
    }

    /*
    * 最大子序列和，返回
    * @param  maxSumRight: include
    * */
    public static long[] maxSubSum(int[] array)
    {
        long maxSum  = Long.MIN_VALUE;
        int  maxSumLeft=0, maxSumRight=0, temp=0, tempLeft=0;
        
        int lens = array.length;
        for (int i = 0; i < lens; i++)
        {
            temp += array[i];
            if (temp > maxSum)
            {
                maxSum = temp;
                maxSumLeft = tempLeft;
                maxSumRight = i;
            }

            if (temp < 0)
            {
                temp = 0;
                tempLeft = i+1;
            }
        }
        return  new long[]{maxSum, maxSumLeft, maxSumRight};
    }

    /*
    * 给定一个整数的数组，相邻的数不能同时选，求从该数组选取若干整数，使得他们的和最大；
    * */
    static int getMax(int a[], int len)
    {
        int max1 = a[0]; //maxSum(n-2);
        int max2 = a[0] > a[1] ? a[0] : a[1]; //maxSum(n-1);如果位置 n-1未选中，则　maxSum(n-1)＝maxSum(n-２)
        int max3 = 0; // maxSum(n-1)
        for (int i = 2; i < len; i++)
        {
            max3 = Max(a[i], Max(max1 + a[i], max2));//考虑数组元素存在负数的情况
            max1 = max2;
            max2 = max3;
        }
        return max3;
    }

    static int Max(int a, int b)
    {
        return a > b ? a : b;
    }
}
