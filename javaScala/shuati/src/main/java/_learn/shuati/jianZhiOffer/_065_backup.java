package _learn.shuati.jianZhiOffer;

import org.junit.Test;

import java.util.Stack;

/**
 * Created by fishsey on 2017/6/30.
 * https://www.nowcoder.com/practice/c61c6999eecb4b8f88a98f66b273a3cc?tpId=13&tqId=11218&rp=4&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
 * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 
 * 例如 a b c e s f c s a d e e 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，
 * 因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
 */
public class _065_backup
{
    @Test
    public  void main()
    {
        char[] matrix = "abcesfcsadee".toCharArray();
        int rows = 3;
        int cols = 4;
        String str = "bcced";
        boolean result = hasPath(matrix, rows, cols, str.toCharArray());
        System.out.println(result);
    }


    public boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        int[][] flags = new int[rows][cols];
        char[][] m = new char[rows][cols];
        for (int i = 0; i < matrix.length; i++)
        {
            int r = i / cols;
            int c = i % cols;
            m[r][c] = matrix[i];
            flags[r][c] = 0;
        }

        Boolean result = false;
        for (int i = 0; i < m.length; i++)
        {
            for (int i1 = 0; i1 < m[i].length; i1++)
            {
                if (m[i][i1] == str[0])
                {
                    result = hasPath(m, flags, i, i1, str);
                    if (result)
                        return result;
                    else
                    {
                        reset(flags);
                    }
                }
            }
        }
        return result;
    }

    private void reset(int[][] flags)
    {
        for (int i = 0; i < flags.length; i++)
        {
            for (int i1 = 0; i1 < flags[i].length; i1++)
            {
                flags[i][i1] = 0;
            }
        }
    }

    //rows and cols is the start point of str
    private Boolean hasPath(char[][] matrix, int[][] flags, int rows, int cols, char[] str)
    {
        flags[rows][cols] = 1;
        Stack<Integer[]> stack = new Stack<>();
        stack.push(new Integer[]{rows, cols});
        while (stack.size()>0 && stack.size()<str.length)
        {
            Integer[] current = stack.peek();
            System.out.print(current[0] + "\t" + current[1] + "\t");

            int[] choice = hasChoice(matrix, flags, current[0], current[1], str[stack.size()]);
            System.out.println(choice[0] + "\t" + choice[1]);
            if (choice[0] == -1)
            {
                stack.pop();
            }else
            {
                stack.push(new Integer[]{choice[0], choice[1]});
            }
        }
        if (stack.size() > 0)
            return true;
        else
            return false;
    }

    private int[] hasChoice(char[][] matrix, int[][] flags, int rows, int cols, char c)
    {

        try
        {
            //left
            if (matrix[rows][cols-1] == c && flags[rows][cols-1]==0)
            {
                flags[rows][cols-1] = 1;
                return new int[]{rows, cols-1};
            }
        } catch (Exception e)
        {
        }
        try
        {
            //right
            if (matrix[rows][cols+1] == c && flags[rows][cols+1]==0)
            {
                flags[rows][cols+1] = 1;
                return new int[]{rows, cols+1};
            }
        } catch (Exception e)
        {
        }
        try
        {
            //up
            if (matrix[rows-1][cols] == c && flags[rows-1][cols]==0)
            {
                flags[rows-1][cols] = 1;
                return new int[]{rows-1, cols};
            }
        } catch (Exception e)
        {
        }
        try
        {
            //down
            if (matrix[rows+1][cols] == c&& flags[rows+1][cols]==0)
            {
                flags[rows+1][cols] = 1;
                return new int[]{rows+1, cols};
            }
        } catch (Exception e)
        {
        }

        return new int[]{-1, -1};
    }

}


