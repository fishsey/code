package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

/**
 * Created by fishsey on 2017/8/24.
 * https://www.nowcoder.com/practice/72a99e28381a407991f2c96d8cb238ab?tpId=49&tqId=29305&tPage=2&rp=2&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */
public class JinDongNianZhongJiang
{
    @Test
    public void test_()
    {
        int[][] map = {{1, 2}, {1, 3}};
        //System.out.println(recur(map, new int[]{0, 0}, new int[]{1, 1}));
        System.out.println(getMostDP(map));
    }

    public int getMost(int[][] board)
    {
        return recur(board, new int[]{0, 0}, new int[]{board.length - 1, board[0].length - 1});
    }

    //by recur
    private int recur(int[][] map, int[] startPoint, int[] endPoint)
    {
        if (startPoint[0] == endPoint[0] && startPoint[1] == endPoint[1])
        {
            return map[endPoint[0]][endPoint[1]];
        }
        int maxValueRight = map[startPoint[0]][startPoint[1]];
        int maxValueDown = map[startPoint[0]][startPoint[1]];
        //right
        if (startPoint[1] + 1 <= endPoint[1])
        {
            maxValueRight += recur(map, new int[]{startPoint[0], startPoint[1] + 1}, endPoint);
        }
        //down
        if (startPoint[0] + 1 <= endPoint[0])
        {
            maxValueDown += recur(map, new int[]{startPoint[0] + 1, startPoint[1]}, endPoint);
        }
        return maxValueRight > maxValueDown ? maxValueRight : maxValueDown;
    }

    public int getMostDP(int[][] board)
    {
        int[][] dp = new int[board.length][board[0].length];
        dp[0][0] = board[0][0];

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (i == 0 && j == 0)
                {
                    //如果是起点坐标，不做任何处理。
                } else if (i == 0)
                {
                    dp[i][j] = dp[i][j - 1] + board[i][j];
                } else if (j == 0)
                {
                    dp[i][j] = dp[i-1][j] + board[i][j];
                } else
                {
                    dp[i][j] = Math.max(dp[i][j - 1] + board[i][j], dp[i-1][j] + board[i][j]);
                }
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }

}
