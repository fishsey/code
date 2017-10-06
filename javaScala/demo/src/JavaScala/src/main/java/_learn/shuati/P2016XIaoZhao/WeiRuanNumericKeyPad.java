package _learn.shuati.P2016XIaoZhao;

import java.util.Scanner;

/**
 * Created by root on 8/22/17.
 * https://www.nowcoder.com/practice/f46db1d185114716abbeaea1d58ffd62?tpId=49&tqId=29235&tPage=1&rp=1&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */
public class WeiRuanNumericKeyPad
{
    private static final int[][] boost = new int[][]{
            {0},
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {0, 2, 3, 5, 6, 8, 9},
            {3, 6, 9},
            {0, 4, 5, 6, 7, 8, 9},
            {0, 5, 6, 8, 9},
            {3, 6, 9},
            {0, 7, 8, 9},
            {0, 8, 9},
            {9}
    };

    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        cin.nextLine();
        while (n-- > 0)
        {
            String number = cin.nextLine();
            System.out.println(getLessMax(number));
        }
    }

    private static StringBuffer getLessMax(String number)
    {
        StringBuffer sb = new StringBuffer();
        int currentPos = 1;
        Boolean flag = false;//mark if has right choice
        getLessMax(number, 0, currentPos, flag, sb);
        return sb;

    }

    private static boolean getLessMax(String number, int i, int currentPos, Boolean flag, StringBuffer sb)
    {
        if (sb.length() == number.length())
            return true;

        if (flag)
        {
            int maxValue = boost[currentPos][boost[currentPos].length-1];
            for (int j=i; j<number.length(); j++)
            {
                sb.append(maxValue+"");
            }
            return true;
        }

        int currentValue = number.charAt(i) - '0';
        for (int j=boost[currentPos].length-1; j>=0; j--)
        {
            if (boost[currentPos][j] > currentValue)
                continue;

            sb.append(boost[currentPos][j]+"");
            if (boost[currentPos][j] < currentValue)
            {
                return getLessMax(number, i+1, boost[currentPos][j], true, sb);
            }else
            {
                if (getLessMax(number, i+1, boost[currentPos][j], false, sb))
                {
                    return true;
                }else
                {
                    sb.deleteCharAt(sb.length()-1);
                }
            }
        }
        return false;
    }
}
