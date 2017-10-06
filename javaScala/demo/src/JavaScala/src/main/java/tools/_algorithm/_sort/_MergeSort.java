package tools._algorithm._sort;

import org.junit.Test;

/**
 * Created by root on 2/16/17.
 */
public class _MergeSort
{
    private static long mergeSortWithRecur(int[] array, int begin, int end)
    {
        if (begin >= end)
            return 0;
        if (end - begin == 1)
        {
            return merge(array, begin, begin, end);
        }
        int mid = (begin + end) >> 1;
        long leftCount = mergeSortWithRecur(array, begin, mid);
        long rightCount = mergeSortWithRecur(array, mid + 1, end);
        long crossCount = merge(array, begin, mid, end);
        return leftCount + rightCount + crossCount;
    }

    //sorted the array with ascend
    //return the number of inversePairs
    private static long merge(int[] array, int begin, int middle, int end)
    {
        int[] temp = new int[end - begin + 1];//store the sorted array
        int index = 0;
        long count = 0; //the numbers of inversePairs
        
        int left = begin;
        int right = middle + 1;
        
        while (left <= middle && right <= end)//put the litter elem into temp and move point
        {
            if (array[left] <= array[right])
                temp[index++] = array[left++];
            else
            {
                //inversePairs appear
                count += middle - left + 1;
                temp[index++] = array[right++];
            }
        }
        if (left <= middle)//copy left remain if exist
        {
            while (left <= middle)
                temp[index++] = array[left++];
        }
        if (right <= end)//copy right remain if exist
        {
            while (right <= end)
                temp[index++] = array[right++];
        }
        System.arraycopy(temp, 0, array, begin, end - begin + 1);
        return count;
    }
    
    @Test
    public void test_()
    {
        int[] array = {1, 2, 3, 4, 5, 0};
        mergeSortWithRecur(array, 0, array.length-1);
        for (int i : array)
        {
            System.out.print(i + " ");
        }
    }
}
