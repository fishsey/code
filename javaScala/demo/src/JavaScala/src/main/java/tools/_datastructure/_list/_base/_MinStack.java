package tools._datastructure._list._base;

import java.util.Stack;


/**
 * Created by fishsey on 2017/1/26.
 */
public class _MinStack<E extends Comparable<E>> extends Stack<E>
{
    public static void main(String args[])
    {
        _MinStack stack = new _MinStack();
        stack.push(-111);
        stack.push(2);
        stack.push(3);
        stack.push(-4);
        stack.push(-10);
        stack.pop();
        System.out.println(stack.peek());
        System.out.println(stack.min());
    }

    private  E minValue;//minValue is the minValue in stack, else null if stack is null

    @Override
    public E push(E node)
    {
        if (this.empty())
            minValue = node;
        if (node.compareTo(minValue) < 0)
        {
            this.minValue = node;
        }
        return super.push(node);
    }

    @Override
    public E pop()
    {
        if (this.empty())
            throw new NullPointerException();

        //if the top-value is minValue, then update the minValue
        E temp = super.pop();
        if (temp.compareTo(this.minValue) == 0)
            this.minValue = updateMinValue();

        return temp;
    }

    private E updateMinValue()
    {
        if (this.empty())
        {
            return null;
        }

        E temp = super.peek();
        for (E e : this)
        {
            if (e.compareTo(temp) < 0)
            {
                temp = e;
            }
        }
        return  temp;
    }

    public E min()
    {
        return this.minValue;
    }
}
