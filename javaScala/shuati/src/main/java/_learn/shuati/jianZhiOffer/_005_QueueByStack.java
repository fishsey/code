package _learn.shuati.jianZhiOffer;

import java.util.Stack;

/**
 * Created by root on 1/17/17.
 */
public class _005_QueueByStack
{
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();


    public void push(int node)
    {
        stack1.push(node);
    }

    public int pop() throws IllegalAccessException
    {
        if (stack1.empty())
            throw  new IllegalAccessException();
        move(stack1, stack2);
        int result = stack2.pop();
        move(stack2, stack1);
        return result;
    }

    private void move(Stack<Integer> stack1, Stack<Integer> stack2)
    {
        while (! stack1.empty())
        {
            stack2.push(stack1.pop());
        }
    }

    public static void main(String args[]) throws IllegalAccessException
    {
        _005_QueueByStack solution = new _005_QueueByStack();

        solution.push(1);
        solution.push(2);
        solution.push(3);
        System.out.println(solution.pop());
        System.out.println(solution.pop());
        System.out.println(solution.pop());
    }
    
}
