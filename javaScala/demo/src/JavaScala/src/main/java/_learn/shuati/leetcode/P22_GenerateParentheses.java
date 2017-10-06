package _learn.shuati.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishsey on 2017/9/11.
 */
public class P22_GenerateParentheses
{
    public List<String> generateParenthesis(int n)
    {
        List<String> result = new ArrayList<String>();
        String paren = "";
        helper(result, paren, 0, 0, n);
        return result;
    }

    private void helper(List<String> result, String paren, int left, int right,int n)
    {
        if (left == n && right == n)
        {
            result.add(paren);
            return;
        }
        if (left < n)//生成左子树
        {
            helper(result, paren + "(", left+1, right, n);
        }
        if (right < n && left > right)//生成右子树，剪枝：左括号大于右括号
        {
            helper(result, paren + ")", left, right+1, n);
        }
    }
}
