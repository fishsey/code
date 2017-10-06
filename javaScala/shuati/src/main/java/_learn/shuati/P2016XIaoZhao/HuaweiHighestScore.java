package _learn.shuati.P2016XIaoZhao;

import java.util.Scanner;

/**
 * Created by root on 7/23/17.
 */
public class HuaweiHighestScore
{
    public static void main(String args[])
    {
        int numStudent, numsStatement;
        Scanner cin = new Scanner(System.in);

        while(cin.hasNext())
        {
            String firstLine = cin.nextLine();
            numStudent = Integer.parseInt(firstLine.trim().split("[ ]+")[0]);
            numsStatement = Integer.parseInt(firstLine.trim().split("[ ]+")[1]);
            int[] ids = new int[numStudent+1];
            initIds(ids, cin, numStudent);
            while (numsStatement-- > 0)
            {
                String[] stat = cin.nextLine().trim().split("[ ]+");
                String flag = stat[0];
                int from = Integer.parseInt(stat[1]);
                int to = Integer.parseInt(stat[2]);
                if (flag.equals("Q"))
                {
                    outPutMaxValue(ids, from, to);
                }
                else
                {
                    update(ids, from, to);
                }
            }
        }
    }

    private static void update(int[] ids, int index, int newValue)
    {
        ids[index] =  newValue;
    }

    private static void outPutMaxValue(int[] ids, int from, int to)
    {
        if (from > to)
        {
            int temp = from;
            from = to;
            to = temp;
        }

        int maxVaue = ids[from];
        for (int i=from; i<=to; i++)
        {
            if (ids[i] > maxVaue)
                maxVaue = ids[i];
        }
        System.out.println(maxVaue);
    }

    private static void initIds(int[] ids, Scanner cin, int numStudent)
    {
        String secondLine = cin.nextLine();
        int i = 1;
        for (String s : secondLine.trim().split("[ ]+"))
        {
            ids[i++] = Integer.parseInt(s);
        }
    }

}
