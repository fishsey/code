package tools._algorithm._search;

/**
 * Created by fishsey on 2017/3/24.
 * 返回查找元素所在的位置
 */
public class BinarySearch
{
    public static void main(String args[])
    {
        int nums = 1 << 8;
        int[] array = new int[nums];
        for (int i = 0; i < nums; i++)
        {
            array[i] = i;
        }
        System.out.println(binarySearch(array, 100));
        System.out.println(binarySearchWithRecur(array, 100));

    }


    public static int binarySearch(int[] array, int a)
    {
        int lo = 0;
        int hi = array.length - 1;
        int mid;
        while (lo <= hi)
        {
            mid = (lo + hi) / 2;
            if (array[mid] == a)
            {
                return mid;
            } else if (array[mid] < a)
            {
                lo = mid + 1;
            } else
            {
                hi = mid - 1;
            }
        }
        return -1;
    }
    
    public static int binarySearchWithRecur(int[] array, int a)
    {
        return binarySearchWithRecur(array, a, 0, array.length);
    }

    public static int binarySearchWithRecur(int[] array, int a, int lo, int hi)
    {
        if (lo <= hi)
        {
            int mid = (lo + hi) / 2;
            if (a == array[mid])
            {
                return mid;
            } else if (a > array[mid])
            {
                return binarySearchWithRecur(array, a, mid + 1, hi);
            } else
            {
                return binarySearchWithRecur(array, a, lo, mid - 1);
            }
        }
        return -1;
    }



}
