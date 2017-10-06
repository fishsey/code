package tools._algorithm._sort;

import java.util.Random;
import java.util.concurrent.RecursiveAction;

/**
 * Created by fishsey on 2017/2/16.
 */
public class _QuickSort<T extends Comparable<T>> extends RecursiveAction
{
    public T[] array;
    public int start, end;
    public static void main(String args[])
    {
        Random random = new Random(11);
        int nums = (int) 1e6;

        long start = System.currentTimeMillis();
        for (int i=0; i<10; i++)
        {
            Integer[] a = createArray(random, nums);

            ////quicksort with recur
            qucikSort(a, 0, a.length - 1);

            ////quicksort with forkJoin
            _QuickSort qs = new _QuickSort(a, 0, a.length-1);
            qs.compute();

            for (int j = 0; j <= 10; j++)
            {
                System.out.print(a[j] + " ");
            }
            System.out.println();

        }
        System.out.println((System.currentTimeMillis()-start));
    }
    
    private static  Integer[] createArray(Random random, int nums)
    {
        Integer[] numbers = new Integer[nums];
        for (int i = 0; i < nums; i++)
        {
            numbers[i] = random.nextInt();
        }
        return numbers;
    }
    
    
    static <T extends Comparable<T>> void qucikSort(T[] a, int start, int end)
    {
        if (start < end)
        {
            int p = core(a, start, end);
            qucikSort(a, start, p - 1);
            qucikSort(a, p + 1, end);
        }
    }

    //return the position of key-elem
    private static <T extends Comparable<T>> int core(T[] a, int start, int end)
    {
        T x = a[end]; //最后一个元素作为枢纽值
        int i = start;//i作为下一个存放大于等于枢纽值的位置
        for (int j = start; j <= end - 1; j++)
        {
            //每次遇见一个 >=x 的值就把它放到 i 位置
            if (a[j].compareTo(x) >= 0)
            {
                swap(a, i, j);//a[i] >= x
                i++;//next position for >=x
            }
        }
        swap(a, i, end);
        return i;//left of i>=a[i], right of i<a[i]
    }

    private static <T extends Comparable<T>> void swap(T[] a, int i, int j)
    {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public _QuickSort(T[] array, int start, int end)
    {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute()
    {
        quirckSortWithForkJoin(array, start, end);
    }

    private void quirckSortWithForkJoin(T[] array, int start, int end)
    {
        if (start < end)
        {
            int p = core(array, start, end);
            _QuickSort qsLeft = new _QuickSort(array, start, p - 1);
            qsLeft.fork();
            _QuickSort qsRight = new _QuickSort(array, p + 1, end);
            qsRight.fork();
            qsRight.join();
            qsLeft.join();
        }
        return;
    }
}
