package tools._algorithm._division;

import java.util.concurrent.RecursiveTask;

/**
 * Created by root on 2/16/17.
 */
public class _MergeSort extends RecursiveTask<Long>
{
    public int[] array;
    public int begin;
    public int end;
    public static final int INTERVALNUM = 1 << 10;

    public _MergeSort(int[] array)
    {
       this(array, 0, array.length-1);
    }

    public _MergeSort(int[] array, int begin, int end)
    {
        this.array = array;
        this.begin = begin;
        this.end = end;
    }

    public Long doCompute()
    {
        return compute();
    }

    @Override
    protected Long compute()
    {
        return mergeSortWithForkJoin(array, begin, end);
    }

    /*
    * return the number of reverse-pairs
    * */
    private static long mergeSortWithForkJoin(int[] array, int begin, int end)
    {
        if (begin >= end)
            return 0;
        if (end - begin <= INTERVALNUM)
        {
            return mergeSortWithRecur(array, begin, end);
        }
        int mid = (begin + end) >> 1;
        _MergeSort mLeft = new _MergeSort(array, begin, mid);
        _MergeSort mRight = new _MergeSort(array, mid + 1, end);
        mLeft.fork();
        mRight.fork();
        return mRight.join() + mLeft.join() + merge(array, begin, mid, end);
    }

    private static long mergeSortWithRecur(int[] array, int begin, int end)
    {
        if (begin >= end)
            return 0;
        if (end - begin <= 2)
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
                //inversePairs appear: middle sorted in left with ascend
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

}
