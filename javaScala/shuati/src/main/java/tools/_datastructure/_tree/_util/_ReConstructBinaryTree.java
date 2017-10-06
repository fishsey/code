package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by root on 1/16/17.
 * 输入某二叉树的前序遍历和中序遍历的结果，
 * 请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
 */
public class _ReConstructBinaryTree
{

    private static TreeNode reConstructBinaryTree(int[] pre, int[] in)
    {
        return reConstructBinaryTree(pre, 0, pre.length-1, in, 0, in.length-1);
    }
    

    private static TreeNode reConstructBinaryTree(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn)
    {
        if (startPre > endPre || startIn > endIn)
            return null;
        
        TreeNode root = new TreeNode(pre[startPre]);

        //the index of val in in-traverse
        int indexIn = mapIndex((int)root.element, in);
        
        //the length of left-tree
        int lens = indexIn - startIn;
    
        root.left = reConstructBinaryTree(pre, startPre+1, startPre+lens, in, startIn, indexIn-1);
        root.right = reConstructBinaryTree(pre, startPre+lens+1, endPre, in, indexIn+1, endIn);
        
        return root;
    }
    
    
    private static int mapIndex(int val, int[] array)
    {
        for (int i=0; i<array.length; i++)
        {
            if (array[i] == val)
                return i;
        }
        return  -1;
    }
}
