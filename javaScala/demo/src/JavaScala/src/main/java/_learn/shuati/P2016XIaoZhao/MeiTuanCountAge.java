package _learn.shuati.P2016XIaoZhao;

import java.util.Scanner;

/**
 * Created by root on 8/17/17.
 */
public class MeiTuanCountAge
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        String[] line;
        int m, y,  n;
        double x;
        while (cin.hasNext())
        {
            line = cin.nextLine().trim().split(" ");
            m = Integer.parseInt(line[0]);
            y = Integer.parseInt(line[1]);
            x = Double.parseDouble(line[2]);
            n = Integer.parseInt(line[3]);
            System.out.println(countAge(m,y, x, n));
        }
    }

    private static int countAge(int m, int y, double x, int n)
    {
        double average = 0;
        double yTemp = y;
        while (n -- > 0)
        {
            yTemp++;
            //average = (int) Math.ceil((m * (1-x) * y + m * x * 21)/m);
            average = (m * (1-x) * yTemp + m * x * 21)/m;
            yTemp = average;
        }
        return (int) Math.ceil(average);
    }
}
