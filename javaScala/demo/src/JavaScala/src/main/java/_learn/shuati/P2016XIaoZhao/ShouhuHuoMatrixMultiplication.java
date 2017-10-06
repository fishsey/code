package _learn.shuati.P2016XIaoZhao;

import java.util.Scanner;

/**
 * Created by fishsey on 2017/9/2.
 * https://www.nowcoder.com/practice/935fbb71542345ef87a7acc190e2577b?tpId=49&tqId=29313&tPage=3&rp=3&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */
public class ShouhuHuoMatrixMultiplication
{
    public static void main(String args[])
    {
        int[][] nums;
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            int n = cin.nextInt();
            int m = cin.nextInt();
            nums = new int[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    nums[i][j] = cin.nextInt();

            System.out.println(getMax(nums));
        }
    }

    private static long getMax(int[][] nums)
    {
        long[][] helper = new long[nums.length][nums[0].length];

        //init with value:1
        for (int i = 0; i < helper.length; i++)
            for (int j = 0; j < helper[0].length; j++)
                helper[i][j] = 1;


        for (int i = 0; i < helper.length; i++)
            for (int j = 0; j < helper[0].length; j++)
                update(helper, i, j, nums[i][j]);

        return getMaxValue(helper);
    }

    private static long getMaxValue(long[][] helper)
    {
        long maxValue = -1;
        for (int i = 0; i < helper.length; i++)
            for (int j = 0; j < helper[0].length; j++)
                maxValue = Math.max(maxValue, helper[i][j]);

        return maxValue;

    }

    private static void update(long[][] helper, int i, int j, int num)
    {
        //update i-row
        for (int k=0; k<helper[0].length; k++)
        {
            if (k == j)
                continue;
            helper[i][k] *= num;
        }

        //update j-row
        for (int k=0; k<helper.length; k++)
        {
            if (k == i)
                continue;
            helper[k][j] *= num;
        }
    }
}
