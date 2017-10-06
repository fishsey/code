package tools._algorithm._sort;

import java.util.ArrayList;

/**
 * Created by fishsey on 2017/3/28.
 */
public class _RadixSort<T>
{
    //sorted Ascend
    public static void radixSort(int[] data, int radix, int d)
    {
        // buckets用于记录待排序元素的信息
        // buckets数组定义了max-min个桶
        ArrayList<Integer>[] buckets = new ArrayList[radix];
        for (int i = 0; i < buckets.length; i++)
        {
            buckets[i] = new ArrayList<>();
        }
        //d趟基数排序。 d 是最大数字的长度
        for (int i = 0, rate = 1; i < d; i++)
        {
            //初始化每个桶为空
            for (ArrayList<Integer> bucket : buckets)
            {
                bucket.clear();
            }
            //分发：将每个数字放入各自所属的桶
            for (int j = 0; j < data.length; j++)
            {
                int subKey = (data[j] / rate) % radix;
                buckets[subKey].add(data[j]);
            }
            //收集
            int index = 0;
            for (ArrayList<Integer> bucket : buckets)
            {
                for (int size = 0; size < bucket.size(); size++)
                {
                    data[index++] = bucket.get(size);
                }
            }
            //更新 rate
            rate *= radix;
        }
    }


    public static void radixSort(int[] data)
    {
        radixSort(data, 10, getD(data));
    }
    
    private static int getD(int[] data)
    {
        int d = 0;
        for (int i : data)
        {
            if (String.valueOf(i).length() > d)
                d = String.valueOf(i).length();
        }
        return d;
    }
    
    public static void main(String args[])
    {
        int[] data = new int[] {1100, 192, 221, 12, 23};
        radixSort(data);
        for (int i : data)
        {
            System.out.println(i);
        }
    }
}
