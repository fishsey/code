package _learn.shuati.P2016XIaoZhao;

import java.util.Scanner;

/**
 * Created by root on 8/11/17.
 */
public class MeiTuanStrNum
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            String line = cin.nextLine();
            String[] keys = line.split(" ");
            int result = process(keys[0],keys[1], Integer.parseInt(keys[2]), Integer.parseInt(keys[3]));
            System.out.println(result);
        }
    }

    static int process(String str1, String str2, int len1, int len2)
    {
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();

        long res = 0;
        for (int i = len1; i <= len2; i++)
        {
            char a = ch1[0];
            char b = ch2[0];
            res += (long) Math.pow(26, i - 1) * (b - a);
            long suma = 0;
            long sumb = 0;
            for (int j = 1; j < ch1.length; j++)// 找到比ch1剩余字符串小的字符串个数
            {
                suma = suma + (ch1[j] - 'a') * (long) Math.pow(26, i - 1 - j);
            }
            for (int j = 1; j < ch2.length; j++)// 找到比ch2剩余字符串小的字符串个数
            {
                sumb = sumb + (ch2[j] - 'a') * (long) Math.pow(26, i - 1 - j);
            }
            res = res + sumb - suma;
        }
        res--;
        res = res % 1000007;
        return (int) res;
    }

}
