package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

/**
 * Created by root on 8/1/17.
 */
public class QunaerBinarySearch
{
    @Test
    public void test_()
    {
        int[] A = {11, 27, 28, 33};
        System.out.println(getPos(A, A.length, 28));
    }

    public int getPos(int[] A, int n, int val)
    {
        return getPos(A, 0, n-1, val);

    }

    private int getPos(int[] a, int left, int right, int val)
    {
        if (left > right)
        {
            return -1;
        }

        int mid = (left + right) >> 1;
        if (a[mid] == val)
        {
            return getVale(mid, a);
        }else if (a[mid] > val)
        {
            return getPos(a, left, mid-1, val);
        }else
        {
            return getPos(a, mid+1, right, val);
        }
    }

    private int getVale(int mid, int[] a)
    {
        while (mid > 0)
        {
            if (a[mid] != a[mid-1])
                return mid;
            mid--;
        }
        return 0;
    }
}
