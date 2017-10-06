package _learn.shuati.leetcode;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by fishsey on 2017/9/12.
 */
public class P79_WordSearch
{
    public boolean exist(char[][] board, String word)
    {
        int[][] flag = new int[board.length][board[0].length];

        ArrayList<int[]> indexs = getStartIndex(board, word.charAt(0));
        for (int[] index : indexs)
        {
            flag[index[0]][index[1]] = 1;
            if (find(index, board, flag, word, 1))
                return true;
            flag[index[0]][index[1]] = 0;

        }
        return false;
    }
    
    private boolean find(int[] startIndex, char[][] board, int[][] flag, String word, int offset)
    {
        //System.out.println(startIndex[0] + " " + startIndex[1]);
        if (offset == word.length())
            return true;

        ArrayList<int[]> choices = getChoice(board, startIndex);
        for (int[] choice : choices)
        {
            if (flag[choice[0]][choice[1]]==0 && board[choice[0]][choice[1]] == word.charAt(offset))
            {
                flag[choice[0]][choice[1]] = 1;
                if (find(choice, board, flag, word, offset + 1))
                    return true;
                flag[choice[0]][choice[1]] = 0;
            }

        }
        return false;
    }
    
    private ArrayList<int[]> getChoice(char[][] board, int[] startIndex)
    {
        ArrayList<int[]> result = new ArrayList<>();

        //up
        if (startIndex[0] - 1 >= 0)
            result.add(new int[]{startIndex[0] - 1, startIndex[1]});
        
        //down
        if (startIndex[0] + 1 < board.length)
            result.add(new int[]{startIndex[0] + 1, startIndex[1]});
        
        //left
        if (startIndex[1] - 1 >= 0)
            result.add(new int[]{startIndex[0], startIndex[1] - 1});
        
        //right
        if (startIndex[1] + 1 < board[0].length)
            result.add(new int[]{startIndex[0], startIndex[1] + 1});
        
        return result;
    }
    
    private ArrayList<int[]> getStartIndex(char[][] board, char c)
    {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++)
        {
            for (int i1 = 0; i1 < board[0].length; i1++)
            {
                if (board[i][i1] == c)
                    result.add(new int[]{i, i1});
            }
        }
        return result;
    }
    
    @Test
    public void test_()
    {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'E', 'S'},
                {'A', 'D', 'E', 'E'}
        };
    
        System.out.println(exist(board, "ABCESEEEFS"));
    }
}
