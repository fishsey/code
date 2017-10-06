package _learn.shuati.classics;

/**
 * Created by root on 2/13/17.
 */
public class MaxSubSum
{
    /*
    * 最大连续子序列和
    * @returnParam  maxSumRight: include
    * */
    public static long[] maxSubSum(int[] array)
    {
        long maxSum = Long.MIN_VALUE;
        int maxSumLeft = 0, maxSumRight = 0, temp = 0, tempLeft = 0;
        
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
                tempLeft = i + 1;
            }
        }
        return new long[]{maxSum, maxSumLeft, maxSumRight};
    }

    /*
    * 给定一个整数的数组，相邻的数不能同时选，求从该数组选取若干整数，使得他们的和最大；
    * 基于动态规划求解dp[n] = max{dp[n-1], dp[n-2]+a[n]}
    * */
    public static int maxSubSumInterval(int a[])
    {
        if (a.length == 0)
            return 0;
        else if (a.length == 1)
            return a[0];
        else
        {
            int max0 = a[0];
            int max1 = a[0] > a[1] ? a[0] : a[1];
            int mexTemp = max1;
            for (int i = 2; i < a.length; i++)
            {
                mexTemp = Math.max(a[i], Math.max(max0 + a[i], max1));//考虑数组元素存在负数的情况
                max0 = max1;
                max1 = mexTemp;
            }
            return mexTemp;
        }
    }

    /*
    * 最大连续乘机和
    * */
    public int maxProduct(int[] A)
    {
        if (A == null || A.length < 1) return 0;
        if (A.length < 2) return A[0];

        int result = A[0];
        int max = A[0], min = A[0];
        for (int i = 1; i < A.length; i++)
        {
            int a = max * A[i];
            int b = min * A[i];
            max = Math.max(A[i], Math.max(a, b));//考虑 A[i]为 负数
            min = Math.min(A[i], Math.min(a, b));//考虑 A[i]为 负数
            result = Math.max(max, result);
        }
        return result;
    }


}
