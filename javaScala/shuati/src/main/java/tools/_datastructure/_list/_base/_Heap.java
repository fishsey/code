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

    /**
     * Create a heap from an array of objects
     */
    public _Heap(E[] objects)
    {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    /**
     * Add a new object into the heap
     */
    public void add(E newObject)
    {
        list.add(newObject); // Append to the heap
        int currentIndex = list.size() - 1; // The index of the last node
        //不断向上调整节点 （父节点*2+1=左边孩子节点， 索引从 0 开始使用）
        while (currentIndex > 0)
        {
            int parentIndex = (currentIndex - 1) / 2;
            //Swap if the current object is greater than its parent
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

    /**
     * Remove the root from the heap
     */
    public E remove()
    {
        if (list.size() == 0)
            return null;
        //根节点位于索引 0 的位置，移除根节点并将最右边的节点放到根节点
        //不断向下调整，直到满足堆条件
        E removedObject = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        //向下调整
        int currentIndex = 0;
        while (currentIndex < list.size())
        {
            //左右孩子结点的索引
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;
            // _001_Find the maximum between two children
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
