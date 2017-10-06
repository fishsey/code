package _learn.shuati.leetcode;

import org.junit.Test;
import tools._datastructure._tree._base.TreeNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by fishsey on 2017/9/2.
 */
public class P32_LongestValidParentheses
{
    public int longestValidParentheses(String s) 
    {
        int maxlens = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);//illegal flag
        for (int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) == '(')
            {
                stack.push(i);
            }else 
            {
                stack.pop();
                if (stack.empty())
                {
                    stack.push(i);//illegal flag
                }
                else
                {
                    maxlens = Math.max(maxlens, i - stack.peek());
                }
            }
        }
        return maxlens;
    }
    
    @Test
    public void test_()
    {
        System.out.println(longestValidParentheses("()()"));

        new PriorityQueue<TreeNode>(Comparator.comparing(node -> node.val));
    }
}

