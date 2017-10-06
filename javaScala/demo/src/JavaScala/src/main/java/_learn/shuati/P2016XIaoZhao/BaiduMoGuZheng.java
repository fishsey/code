package _learn.shuati.P2016XIaoZhao;

import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/ed9bc679ea1248f9a3d86d0a55c0be10?tpId=49&tqId=29290&tPage=2&rp=2&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */

public class BaiduMoGuZheng
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int n, m, k;
        int[][] flag;
        String[] strline = null;

        while (cin.hasNext())
        {
            n = cin.nextInt();
            m = cin.nextInt();
            k = cin.nextInt();
            cin.nextLine();
            flag = new int[n][m];
            while (k-- > 0)
            {
                strline = cin.nextLine().split(" ");
                flag[Integer.parseInt(strline[0]) - 1][Integer.parseInt(strline[1]) - 1] = 1;
            }
            System.out.printf("%.2f\n", getProbRecur(flag, new int[]{0, 0}));
        }
    }

    private static double getProbRecur(int[][] flag, int[] start)
    {
        int n = flag.length - 1;
        int m = flag[0].length - 1;

        //reach end
        if (start[0] == n && start[1] == m)
        {
            return 1.0 - flag[start[0]][start[1]];
        }

        //recur
        //down
         if (start[0] + 1 <= n && start[1] + 1 > m)
        {
            if (flag[start[0]+1][start[1]] == 1)
                return 0;
            else
                return  getProbRecur(flag, new int[]{start[0]+1, start[1]});

        }
         //right
         else if (start[1] + 1 <= m && start[0] + 1 > n)
        {
            if (flag[start[0]][start[1]+1] == 1)
                return 0;
            else
                return getProbRecur(flag, new int[]{start[0], start[1]+1});
        }
         //down and right
         else if (start[0] + 1 <= n && start[1] + 1 <= m)
         {
             if (flag[start[0]+1][start[1]] == 1 && flag[start[0]][start[1]+1] == 1)
                 return 0;
             else if (flag[start[0]+1][start[1]] == 1)
                 return 0.5 * getProbRecur(flag, new int[]{start[0], start[1]+1});
             else if (flag[start[0]][start[1]+1] == 1)
                 return 0.5 * getProbRecur(flag, new int[]{start[0]+1, start[1]});
             else
                 return 0.5 * getProbRecur(flag, new int[]{start[0]+1, start[1]}) + 0.5 * getProbRecur(flag, new int[]{start[0], start[1]+1});

         }

        return -1;
    }



}
