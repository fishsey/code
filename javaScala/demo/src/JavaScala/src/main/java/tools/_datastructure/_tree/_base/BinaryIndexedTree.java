package tools._datastructure._tree._base;

import java.util.Arrays;

/**
 * Created by root on 2/16/17.
 */
public class BinaryIndexedTree
{
    private int[] array;
    //tree[0] discard, trer[i] = sum(以结点i为根节点的左子树) + array[i]，以索引建立的二叉索引树
    public int[] tree;
    private int n;
    
    public BinaryIndexedTree(int[] array)
    {
        this.array = array;
        this.n = this.array.length;
        this.tree = new int[this.n+1];
        preProcess(); //init the int[]-tree
    }
    
    public void add(int index, int increase)
    {
        updateTree(index,  increase);
    }
    
    
    public int sum(int index)
    {
        int result = 0;
        while (index >= 1)
        {
            result += this.tree[index];
            index -= lowbit(index);//right child  ->  parent
        }
        return result;
    }
    
    private void preProcess()
    {
        for (int i = 0; i < n; i++)
        {
            updateTree(i+1, this.array[i]);
        }
    }
    
    @Override
    public String toString()
    {
        return "BinaryIndexedTree{" +
                "tree=" + Arrays.toString(tree) +
                '}';
    }
    
    //the index-postion add value, index is the number in tree, begin from 1
    //means the a[index-1] = a[index-1] + value
    private void updateTree(int index, int value)
    {
        while (index <= n)
        {
            this.tree[index] += value;
            index += lowbit(index);//left child ->  parent
        }
    }

    private int lowbit(int index)
    {
        return index & (-index);
    }

    public static void main(String args[])
    {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 9, 9, 10, 11, 12, 13, 14, 15};
    
        BinaryIndexedTree biTree = new BinaryIndexedTree(array);
        System.out.println(biTree);
        //biTree.add(7, 1);
        //System.out.println(biTree);
        
    }
}
