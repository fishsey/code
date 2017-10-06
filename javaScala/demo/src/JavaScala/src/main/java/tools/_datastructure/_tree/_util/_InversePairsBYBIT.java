package tools._datastructure._tree._util;

import tools._datastructure._tree._base.BinaryIndexedTree;


/**
 * Created by root on 6/1/17.
 * 逆序对
 */
public class _InversePairsBYBIT
{
    private static int inversePairsWithBIT(int[] array)
    {
        //get the maxValue in array and the tree-array has maxValue+1 length
        int maxValue = getMaxValue(array);

        //init the binary-index-tree
        int[] maxArray = new int[maxValue+1];
        BinaryIndexedTree tree = new BinaryIndexedTree(maxArray);

        //get the number of inverse pair
        int count = 0;
        for (int i : array)
        {
            int position = i+1;//the (i+1)-th position in tree-array
            tree.add(position, 1);
            count += tree.sum(maxArray.length) - tree.sum(position);
        }
        return count;
    }

    private static int getMaxValue(int[] array)
    {
        int maxValue = array[0];
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] > maxValue)
                maxValue = array[i];
        }
        return maxValue;
    }

    public static void main(String args[])
    {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 0};
        System.out.println(inversePairsWithBIT(array));
    }

}
