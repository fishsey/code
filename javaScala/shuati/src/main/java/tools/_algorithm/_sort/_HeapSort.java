package tools._algorithm._sort;


import tools._datastructure._list._base._Heap;

public class _HeapSort
{
    /**
     * _Heap sort method
     */
    public static <E extends Comparable> void heapSort(E[] list)
    {
        //Create a _Heap of integers
        _Heap<E> heap = new _Heap<E>();
        for (int i = 0; i < list.length; i++)
            heap.add(list[i]);

        //Remove elements from the heap and stored in list
        for (int i = list.length - 1; i >= 0; i--)
            list[i] = heap.remove();
    }

    /**
     * A test method
     */
    public static void main(String[] args)
    {
        Integer[] list = {2, 3, 2, 5, 6, 1, -2, 3, 14, 12};
        heapSort(list);
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i] + " ");
    }
}
