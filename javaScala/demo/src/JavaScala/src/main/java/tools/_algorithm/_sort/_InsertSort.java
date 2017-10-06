package tools._algorithm._sort;

import org.junit.Test;

/**
 * Created by root on 2/16/17.
 */
public class _InsertSort
{
    @Test
    public void test_()
    {
        int[] array = {1, 2, 2, 0, 5, 0};
        insertSort(array);
        for (int i : array)
        {
            System.out.print(i + " ");
        }
    }

    private void insertSort(int[] array)
    {
        for (int i=1; i<array.length; i++)
        {
            int j=i-1;
            for (; j>=0; j--)
            {
                if (array[j] <= array[i])
                {
                    insert(array, j, i);
                    break;
                }
            }
            if (j == -1)
                insert(array, j, i);
        }
    }

    //j 后面插入 i
    private void insert(int[] array, int j, int i)
    {
        int temp = array[i];
        for (int k=i-1; k>=j+1; k--)
            array[k+1] = array[k];
        array[j+1] = temp;
    }
}
