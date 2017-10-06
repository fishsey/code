package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

/**
 * Created by root on 8/16/17.
 */
public class MeiTuanVisitDp
{
    public static final int row = -1;//from down to upper
    public static int column = 1;//right

    private int[] getStart(int[] p1, int[] p2)
    {
        return p1[0] > p2[0] ? p1 : p2;
    }

    private int[] locationPosition(int[][] map, int i)
    {
        for (int i1 = 0; i1 < map.length; i1++)
        {
            for (int i2 = 0; i2 < map[0].length; i2++)
            {
                if (map[i1][i2] == i)
                    return new int[]{i1, i2};
            }
        }
        return null;
    }

    @Test
    public void test_()
    {
        int[][] map = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2},};
        System.out.println(countPath(map, 7, 5));
    }

    public int countPath(int[][] map, int n, int m)
    {
        int[] P1 = locationPosition(map, 1);
        int[] P2 = locationPosition(map, 2);
        int[] startPoint = getStart(P1, P2);
        int[] endPoint = startPoint == P1 ? P2 : P1;//from down to upper
        return countPath(map, startPoint, endPoint);
    }


    public int countPath(int[][] map, int[] startPoint, int[] endPoint)
    {
        if (startPoint[1] > endPoint[1])
            column = -1;//left
        return dp(map, startPoint, endPoint);
    }

    //by dp
    private int dp(int[][] map, int[] startPoint, int[] endPoint)
    {
        int[][] dp = new int[map.length][map[0].length];
        for (int x = startPoint[0]; x >= endPoint[0]; x += row)//up
        {
            for (int y = startPoint[1]; y != endPoint[1]+column; y += column)//left(column=-1) or right(column=1)
            {
                if (x == startPoint[0] && y == startPoint[1])
                {
                    dp[x][y] = 1;
                }else if (x == startPoint[0])
                {
                    dp[x][y] = map[x][y] == -1 ? 0 : dp[x][y-column];
                } else if (y == startPoint[1])
                {
                    dp[x][y] = map[x][y] == -1 ? 0 : dp[x-row][y];
                }else
                {
                    dp[x][y] = map[x][y] == -1 ? 0 : dp[x][y-column] + dp[x-row][y];
                }
            }
        }
        return dp[endPoint[0]][endPoint[1]];
    }


}
