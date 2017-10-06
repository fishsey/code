package _learn.shuati.leetcode;

/**
 * Created by fishsey on 2017/9/10.
 */
public class P4_MedianOfTwoSortedArrays
{
    public static double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
        int m = nums1.length;
        int n = nums2.length;
        //System.out.println(m + "\t" + n);
        if (n == 0 && m == 0)
            throw new IllegalArgumentException("No two sum solution");
        else if (m == 0 && n != 0)
        {
            if (n % 2 == 0)
                return (nums2[n / 2 - 1] + nums2[n / 2]) / 2.0;
            else
                return nums2[n / 2];
        }
        else if (n == 0 && m != 0)
        {
            if (m % 2 == 0)
                return (nums1[m / 2 - 1] + nums1[m / 2]) / 2.0;
            else
                return nums1[m / 2];
        }
        else if ((m + n) % 2 == 0)//even
            return (findMedia(nums1, nums2, (m + n) / 2) + findMedia(nums1, nums2, (m + n) / 2 + 1)) / 2.0;
        else//odd
            return findMedia(nums1, nums2, (m + n + 1) / 2);

    }

    private static double findMedia(int[] nums1, int[] nums2, int k)
    {
        int p1 = 0, p2 = 0, kTemp = k;
        int m = nums1.length;
        int n = nums2.length;
        while (true)
        {
            if (k == 1)
                return nums1[p1] < nums2[p2] ? nums1[p1] : nums2[p2];
            int v1 = k / 2 + p1 - 1 > m - 1 ? m - 1 : k / 2 + p1 - 1;
            int v2 = k / 2 + p2 - 1 > n - 1 ? n - 1 : k / 2 + p2 - 1;
            if (nums1[v1] < nums2[v2])
            {
                k = kTemp - v1 - 1 - p2;
                p1 = v1 + 1;
                if (p1 == m)
                    return nums2[k - 1 + p2];
            } else
            {
                k = kTemp - v2 - 1 - p1;
                p2 = v2 + 1;
                if (p2 == n)
                    return nums1[k - 1 + p1];
            }
        }
    }
}
