package tools._datastructure._list._base;

/**
 * Created by fishsey on 2017/3/23.
 */

import java.io.Serializable;
import java.util.Arrays;

public class LoopQueue<T> implements Serializable
{
    private static final int DEFAULT_SIZE = 1<<4;
    private int capacity;//保存数组的长度
    private Object[] elementData;//定义一个数组用于保存循环队列的元素
    private int front = 0;//队头，头元素的位置
    private int rear = 0;//队尾,下一个插入的位置(该位置留空不用)


    public LoopQueue()
    {
        this(DEFAULT_SIZE);
    }

    public LoopQueue(int initSize)
    {
        this.capacity = initSize;
        elementData = new Object[capacity];
    }

    public int size()
    {
        if (isEmpty())
        {
            return 0;
        }
        return rear > front ? rear - front : capacity - (front - rear);
    }

    public void add(T element)
    {
        if (isFull())
        {
            throw new IndexOutOfBoundsException("队列已满的异常");
        }
        elementData[rear++] = element;
        //如果rear已经到头，那就转头
        rear = rear == capacity ? 0 : rear;
    }

    //移除队列头元素,返回旧值
    public T poll()
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("空队列异常");
        }
        T oldValue = (T) elementData[front];
        elementData[front++] = null;
        front = front == capacity ? 0 : front;
        return oldValue;
    }

    //返回队列顶元素，但不删除队列顶元素
    public T peek()
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("空队列异常");
        }
        return (T) elementData[front];
    }

    public boolean isEmpty()
    {
        return rear == front && elementData[front] == null;
    }

    public boolean isFull()
    {
        return rear == front && elementData[front] != null;
    }

    public void clear()
    {
        Arrays.fill(elementData, null);
        front = 0;
        rear = 0;
    }

    public static void main(String[] args)
    {
        LoopQueue<Integer> lpQueue = new LoopQueue<>();
        for (int i=0; i<16; i++)
            lpQueue.add(i);
        System.out.println(lpQueue.isEmpty());
        System.out.println(lpQueue.isFull());
        System.out.println(lpQueue.front + " " + lpQueue.rear);
    }
}
