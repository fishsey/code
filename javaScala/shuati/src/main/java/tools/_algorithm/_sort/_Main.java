package tools._algorithm._sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by root on 5/29/17.
 */
public class _Main
{

    private static int keyGenerate(Integer[] numbersCopy, Integer[] numbersIndex, Integer x)
    {
        for (int i = 0; i < numbersCopy.length; i++)
        {
            if (numbersCopy[i] == x)
            {
                return numbersIndex[i];
            }
        }
        return -1;
    }

    @Test
    public void keyGenerateTest()
    {
        Integer[] numbers = {6, 5, 1, 4, 2, 3};
        Integer[] numbersCopy = numbers.clone();
        Integer[] numbersIndex = {1, 3, 11, 5, 9, 7};

        Arrays.sort(numbers, Comparator.comparing(x -> keyGenerate(numbersCopy, numbersIndex, x)));
        Arrays.stream(numbers).forEach(System.out::println);
    }
}
