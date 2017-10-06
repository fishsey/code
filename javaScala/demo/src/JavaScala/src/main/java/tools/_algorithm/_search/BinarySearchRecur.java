package tools._algorithm._search;

/**
 * Created by fishsey on 2017/3/24.
 * 返回查找元素所在的位置
 */
public class BinarySearchRecur
{
    public static void main(String args[])
    {
        int nums = 1 << 8;
        int[] array = new int[nums];
        for (int i = 0; i < nums; i++)
        {
            array[i] = i;
        }
        System.out.println(binarySearchWithRecur(array, 100));

    }


    public static int binarySearchWithRecur(int[] array, int value)
    {
        return binarySearchWithRecur(array, value, 0, array.length);
    }

    public static int binarySearchWithRecur(int[] array, int value, int lo, int hi)
    {
        if (lo <= hi)
        {
            int mid = (lo + hi) / 2;
            if (value == array[mid])
            {
                return mid;
            } else if (value > array[mid])
            {
                return binarySearchWithRecur(array, value, mid + 1, hi);
            } else
            {
                return binarySearchWithRecur(array, value, lo, mid - 1);
            }
        }
        return -1;
    }



}
