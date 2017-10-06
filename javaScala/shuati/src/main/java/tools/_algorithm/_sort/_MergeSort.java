package tools._algorithm._sort;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

/**
 * Created by root on 2/16/17.
 */
public class _MergeSort extends RecursiveTask<Long>
{
    public int[] array;
    public int begin;
    public int end;
    public long inverNums;

    public _MergeSort(int[] array, int begin, int end)
    {
        this.array = array;
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute()
    {
        return mergeSortWithForkJoin(array, begin, end);
    }

    private static long mergeSortWithForkJoin(int[] array, int begin, int end)
    {
        if (begin >= end)
            return 0L;
        if (end - begin <= 1024 * 16)
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

    //sorted the array with desc
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

    private static int[] createArray(int nums)
    {
       return createArray(nums, 11);
    }

    private static int[] createArray(int nums, int seed)
    {
        Random random = new Random(seed);
        int[] numbers = new int[nums];
        for (int i = 0; i < nums; i++)
        {
            numbers[i] = random.nextInt();
        }
        return numbers;
    }

    public static void main(String args[])
    {

        int nums = (int) 1e7;
        int[] a = new int[nums];

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++)
        {
            System.out.println(i);
            a = createArray(nums, i);
            ////sort by Arrays
            //Arrays.parallelSort(a);
            //////mergeSortWithRecur
            //long inverNum = mergeSortWithRecur(a, 0, a.length - 1);
            ////mergeSortWithForkJoin
            _MergeSort mergeSort = new _MergeSort(a, 0, a.length - 1);
            long inverNum = mergeSort.compute();
        }
        System.out.println((System.currentTimeMillis() - start));
        //output the sorted result
        for (int i = 0; i <= 5; i++)
        {
            System.out.print(a[i] + " ");
        }

    }
}
