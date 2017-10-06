package tools._datastructure._list._util;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Created by fishsey on 2017/1/26.
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。
 * 例如序列 1,2,3,4,5是某栈的压入顺序，序列 4，5,3,2,1是该压栈序列对应的一个弹出序列，
 * 但 4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的)
 * https://www.nowcoder.com/practice/d77d11405cc7470d82554cb392585106?tpId=13&tqId=11174&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 */
public class _IsPopOrder<E extends Comparable<E>>
{
    @Test
    public void test()
    {
        Integer[] push = {1, 2, 3, 4, 5};
        Integer[] pop1 = {4, 5, 3, 2, 1};
        Integer[] pop2 = {4, 3, 5, 1, 2};

        assertEquals(_IsPopOrder.IsPopOrder(push, pop1), true);
        assertEquals(_IsPopOrder.IsPopOrder(push, pop2), false);
    }

	//模拟出栈 入栈的行为判断
    public static <E> boolean IsPopOrder(E[] pushA, E[] popA)
    {
        if (pushA == null || popA == null)
            return false;

        Stack stack = new Stack();

        for (int i = 0, j=0; i < pushA.length; i++)
        {
            E elem = pushA[i];
            stack.push(elem);
            while (!stack.empty() && popA[j] == stack.peek())
            {
                stack.pop();
                j++;
            }
        }
        if (stack.isEmpty())
            return true;
        else
            return  false;
    }



}
