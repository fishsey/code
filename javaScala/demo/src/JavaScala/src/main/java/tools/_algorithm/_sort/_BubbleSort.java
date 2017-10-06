package tools._algorithm._sort;

import java.util.Random;

public class _BubbleSort
{
    public static void bubbleSort(int[] list)
    {
        for (int k=list.length-1; k>0; k--)
        {
            boolean existExchange = false;
            for (int i = 0; i < k; i++)
            {
                if (list[i] > list[i + 1])
                {
                    int temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;
                    existExchange = true;
                }
            }
            if (!existExchange)
                break;
        }
    }

    private static  int[] createArray(int nums)
    {
        Random random = new Random(11);
        int[] numbers = new int[nums];
        for (int i = 0; i < nums; i++)
        {
            numbers[i] = random.nextInt();
        }
        return numbers;
    }

    /**
     * A test method
     */
    public static void main(String[] args)
    {
        int[] list = createArray(10);
        bubbleSort(list);
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i] + " ");
    }
}
