package tools._algorithm._division;

/**
 * Created by fishsey on 2017/4/24.
 */
public class _BinarySearch
{
    /*
    * 返回索引
    * */
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


    public static int binarySearchWithRecur(int[] array, int elem)
    {
        return binarySearchWithRecur(array, 0, array.length-1, elem);
    }

    public static int binarySearchWithRecur(int[] array, int begin, int end, int elem)
    {
       if (begin > end)
           return begin;
       else
       {
           int middle = (begin + end) >> 1;
           if (array[middle] == elem)
               return middle;
           else if (array[middle] > elem)
           {
              return binarySearchWithRecur(array,begin, middle-1, elem);
           }
           else if (array[middle] < elem)
           {
              return binarySearchWithRecur(array, middle+1, end, elem);
           }
       }
        return -1;

    }
}
