package tools._algorithm._sort;

import org.junit.Test;

/**
 * Created by fishsey on 2017/2/16.
 */
public class _QuickSort
{
    static <T extends Comparable<T>> void qucikSort(T[] a, int start, int end)
    {
        if (start < end)
        {
            int p = core(a, start, end);
            qucikSort(a, start, p - 1);
            qucikSort(a, p+1, end);
        }
    }

    //return : left(include)-i >= x, right < x
    //descend
    private static <T extends Comparable<T>> int core(T[] a, int start, int end)
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
        return i;
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
        qucikSort(nums, 0, nums.length - 1);
        for (Integer num : nums)
        {
            System.out.print(num + " ");
        }
    }
}
