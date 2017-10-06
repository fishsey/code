package tools._algorithm._sort;

/**
 * Created by fishsey on 2017/9/15.
 */
public class _ShellSort
{
    public static void shellSort(int[] arrays)
    {
        if (arrays == null || arrays.length <= 1)
        {
            return;
        }

        int incrementNum = arrays.length / 2;
        while (incrementNum >= 1)
        {
            for (int i = 0; i < incrementNum; i++)
            {
                sort(i, arrays, incrementNum);
            }
            incrementNum = incrementNum / 2;
        }
    }

    //选择排序， 每次将最小的值放入左边
    private static void sort(int i, int[] arrays, int incrementNum)
    {
        for (int j=i; j<arrays.length; j+=incrementNum)
        {
            int minIndex = j;
            int minValue = arrays[j];
            for (int k=j; k<arrays.length; k+=incrementNum)
            {
                if (arrays[k] < minValue)
                {
                    minValue = arrays[k];
                    minIndex = k;
                }
            }
            if (minIndex != j)
            {
                arrays[minIndex] = arrays[j];
                arrays[j] = minValue;
            }
        }
    }

    public static void main(String args[])
    {
        int[] data = new int[] {12, 4, 8, 12, 23, 3, 4, 6};
        shellSort(data);
        for (int i : data)
        {
            System.out.print(i + " ");
        }
    }
}
