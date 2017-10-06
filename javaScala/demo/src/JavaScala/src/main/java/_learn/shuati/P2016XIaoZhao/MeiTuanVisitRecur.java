package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by root on 8/16/17.
 */
public class MeiTuanVisitRecur
{
    public static final int row = -1;//from down to upper
    public static int column = 1;//right

    private int[] getStart(int[] p1, int[] p2)
    {
        return  p1[0] > p2[0] ? p1 : p2;
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
                {0, 0, 0, 0, 2}, };
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
        return  recur(map, startPoint, endPoint);
    }


    //by recur and bfs
    private int recur(int[][] map, int[] startPoint, int[] endPoint)
    {
        if (startPoint[0] == endPoint[0] && startPoint[1] == endPoint[1])
            return 1;
        ArrayList<int[]> choice = getchoice(map, startPoint);
        int count = 0;
        for (int[] ints : choice)
        {
            count += recur(map, ints, endPoint);
        }
        return count;
    }

    private ArrayList<int[]> getchoice(int[][] map, int[] startPoint)
    {
        ArrayList<int[]> result = new ArrayList<>();

        //row: up
        if (startPoint[0] + row >= 0  && map[startPoint[0] + row][startPoint[1]] != -1)
        {
            result.add(new int[]{startPoint[0] + row, startPoint[1]});
        }
        //column: left or right
        if (startPoint[1]+column>=0 && startPoint[1]+column<map[0].length && map[startPoint[0]][startPoint[1]+column] != -1)
        {
            result.add(new int[]{startPoint[0], startPoint[1]+column});
        }
        return result;
    }


}
