package tools._datastructure._list._util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by root on 2/14/17.
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
 */
public class MinNumberComb
{
    public static void main(String args[])
    {
        int[] numbers = {3, 32, 321};
        System.out.println(printMinNumber(numbers));
        
    }
    
    private static String printMinNumber(int[] numbers)
    {
        //trans int[] to string[]
        int lens = numbers.length;
        String[] numStr = new String[lens];
        for (int i = 0; i < lens; i++)
        {
            numStr[i] = String.valueOf(numbers[i]);
        }
        
        //sort the string[] by ascend
        //define comparator: if a+b < b+a, then a < b
        //Arrays.sort(numStr, (x, y)-> (x+y).compareTo(y+x));
        Arrays.sort(numStr, new Comparator<String>()
        {
            @Override
            public int compare(String x, String y)
            {
                return (x+y).compareTo(y+x);
            }
        });

        //get the result
        StringBuffer result = new StringBuffer();
        for (String s : numStr)
        {
            result.append(s);
        }
        return result.toString();
    }
}
