package _learn.shuati.jianZhiOffer;

import java.util.Arrays;

/**
 * Created by fishsey on 2017/1/24.
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class _013_reOrderArray
{
    public static void main(String args[])
    {
        int[] test = {1, 4, 1, 2, 3, 4, 5, 6};
        reOrderArray(test);
        Arrays.stream(test).forEach(System.out::print);

    }

    public static void reOrderArray(int [] array)
    {
       for (int i=0; i<array.length; i++)
       {
           //odd
           if (array[i] % 2 != 0)
               moveToHead(array, i);
       }
    }

    private static void moveToHead(int[] array, int i)
    {
        int temp = array[i];

        //move right one step of all the even-number
        i--;
        while (i >=0  && array[i] % 2 == 0)
        {
            array[i+1] = array[i];
            i--;
        }
        array[i+1] = temp;
    }

}
