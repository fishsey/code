package tools._datastructure._list._base;


/**
* 大堆
*/
public class _Heap<E extends Comparable>
{
    private java.util.ArrayList<E> list = new java.util.ArrayList<E>();

    public _Heap()
    {
    }

    public _Heap(E[] objects)
    {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    public void add(E newObject)
    {
        //不断向上调整节点 （父节点*2+1=左边孩子节点， 索引从 0 开始使用）
        list.add(newObject);
        int currentIndex = list.size() - 1;
        while (currentIndex > 0)
        {
            int parentIndex = (currentIndex - 1) / 2;
            if (list.get(currentIndex).compareTo(list.get(parentIndex)) > 0)
            {
                E temp = list.get(currentIndex);
                list.set(currentIndex, list.get(parentIndex));
                list.set(parentIndex, temp);
            } else
                break; // the tree is a heap now
            currentIndex = parentIndex;
        }
    }

    public E remove()
    {
        if (list.size() == 0)
            return null;
        E removedObject = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);

        //向下调整
        int currentIndex = 0;
        while (currentIndex < list.size())
        {
            // 左右孩子结点的索引
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            // Find the maximum between two children
            if (leftChildIndex >= list.size())
                break;
            int maxIndex = leftChildIndex;
            if (rightChildIndex < list.size())
            {
                if (list.get(leftChildIndex).compareTo(list.get(rightChildIndex)) < 0)
                {
                    maxIndex = rightChildIndex;
                }
            }

            // Swap if the current node is less than the maximum
            if (list.get(currentIndex).compareTo(list.get(maxIndex)) < 0)
            {
                E temp = list.get(maxIndex);
                list.set(maxIndex, list.get(currentIndex));
                list.set(currentIndex, temp);
                currentIndex = maxIndex;
            } else
                break; // The tree is a heap
        }
        return removedObject;
    }

    /**
     * Get the number of nodes in the tree
     */
    public int getSize()
    {
        return list.size();
    }
}
