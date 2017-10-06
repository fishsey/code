package _learn.shuati.temp;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by root on 8/15/17.
 */

public class Main
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            String line = cin.nextLine();
            int maxValue = 0;
            ArrayList<Integer> input = TransToArray(line);
            ArrayList<Integer> temp  = new ArrayList<>(); ;
            for (int i = 0; i < input.size(); i++)
            {
                if (input.get(i) != 0)
                {
                    temp.add(input.get(i));
                } else
                {
                    int[] numbers = getArray(temp);
                    temp.clear();
                    maxValue += maxScore(numbers);
                }
            }
            if (temp.size() > 0)
            {
                int[] numbers = getArray(temp);
                temp.clear();
                maxValue += maxScore(numbers);
            }
            System.out.println(maxValue);
        }
    }

    private static int[] getArray(ArrayList<Integer> temp)
    {
        int[] as = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++)
        {
            as[i] = temp.get(i);
        }
        return as;
    }

    private static ArrayList<Integer> TransToArray(String line)
    {
        ArrayList<Integer> temp = new ArrayList<>();
        for (String s : line.trim().split("[ ]+"))
        {
            temp.add(Integer.parseInt(s));
        }
        return temp;
    }

    public static int maxScore(int[] nums)
    {
        int n = nums.length + 2;
        int[] newnums = new int[n];
        for (int i = 0; i < n - 2; i++)
        {
            newnums[i + 1] = nums[i];
        }
        newnums[0] = newnums[n - 1] = 1;
        int[][] DP = new int[n][n];
        for (int k = 2; k < n; k++)
        {
            for (int l = 0; l + k < n; l++)
            {
                int h = l + k;
                for (int m = l + 1; m < h; m++)
                {
                    DP[l][h] = Math.max(DP[l][h], newnums[l] * newnums[m] * newnums[h] + DP[l][m] + DP[m][h]);
                }
            }
        }
        return DP[0][n - 1];
    }
}
