package _learn.shuati.leetcode;

import org.junit.Test;

/**
 * Created by fishsey on 2017/9/15.
 */
public class P96_UniqueBinarySearchTrees
{
    //fn = sums(f(k) * f(n-1-k) (for k=0 to k=n-1))
    public int numTrees(int n)
    {
        if (n<=1)
            return 1;
        int[] result = new int[n+1];
        result[0] = 1;
        result[1] = 1;
        for (int i=2; i<=n; i++)
        {
            for (int k=0; k<=i-1; k++)
                result[i] += result[k]*result[i-1-k];
        }
        return result[n];
    }

    @Test
    public void test_()
    {
        System.out.println(numTrees(3));
    }
}
