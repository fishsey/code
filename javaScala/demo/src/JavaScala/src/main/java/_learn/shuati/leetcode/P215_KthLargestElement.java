package _learn.shuati.leetcode;

import org.junit.Test;

public class P215_KthLargestElement<T extends Comparable<T>>
{
    public static <T extends Comparable<T>> T findKthLargest(T[] nums, int k)
    //public T findKthLargest(T[] nums, int k)
    {
        return findKthLargest(nums, k, 0, nums.length-1);
    }

    public static <T extends Comparable<T>> T findKthLargest(T[] a, int k, int start, int end)
    {
        int middle = (end+start)/2;
        T x = a[middle];
        swap(a, middle, end);
        int i = start;//i作为下一个存放大于等于枢纽值的位置
        for (int j = start; j <= end-1; j++)
        {
            //每次遇见一个 >=x 的值就把它放到 i 位置
            if (a[j].compareTo(x) >= 0)
            {
                swap(a, i, j);//a[i] >= x
                i++;//next position for >=x
            }
        }
        swap(a, i, end);

        int kTemp = i-start+1;
        if (kTemp == k)
            return a[i];
        else if (kTemp < k)
        {
            return findKthLargest(a, k-kTemp, i+1, end);
        }else
        {
            return findKthLargest(a, k, start, i-1);
        }
    }

    private static <T extends Comparable<T>> void swap(T[] a, int i, int j)
    {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    @Test
    public void test_()
    {
        Integer[] nums = {1, 12, 20, 6, 4, 5, 3, 11, 2, 3};
        System.out.println(findKthLargest(nums, 4));
    }
}
