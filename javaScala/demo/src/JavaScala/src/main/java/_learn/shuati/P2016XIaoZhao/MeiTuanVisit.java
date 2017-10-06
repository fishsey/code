package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

import java.util.Stack;

/**
 * Created by root on 8/16/17.
 */
public class MeiTuanVisit
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
        return  bfs(map, startPoint, endPoint);
    }

    //by bfs
    private int bfs(int[][] map, int[] startPoint, int[] endPoint)
    {
        int count = 0;
        Stack<int[]> stack = new Stack();
        stack.push(startPoint);
        if (startPoint[0] == endPoint[0] && startPoint[1] == endPoint[1])
        {
            count++;
        }
        while (stack.size() > 0)
        {
            int[] peek = stack.pop();
            count += pushStack(map, peek, endPoint, stack);
        }
        return count;
    }

    private int pushStack(int[][] map, int[] peek, int[] endPoint, Stack<int[]> stack)
    {
        int count = 0;
        //row: up
        if (peek[0] + row >= 0  && map[peek[0] + row][peek[1]] != -1)
        {
            stack.push(new int[]{peek[0] + row, peek[1]});
            if (peek[0] + row == endPoint[0] && peek[1] == endPoint[1])
            {
                count ++;
            }
        }
        //column: left or right
        if (peek[1]+column>=0 && peek[1]+column<map[0].length && map[peek[0]][peek[1]+column] != -1)
        {
            stack.push(new int[]{peek[0], peek[1]+column});
            if (peek[0] == endPoint[0] && peek[1]+column == endPoint[1])
            {
                count ++;
            }
        }
        return count;
    }



}
