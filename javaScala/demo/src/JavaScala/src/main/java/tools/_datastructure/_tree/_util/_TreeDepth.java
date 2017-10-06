package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by root on 2/17/17.
 */
public class _TreeDepth
{
    public static void main(String args[])
    {
    
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.left.right.left = new TreeNode(5);
        root1.left.right.left.left = new TreeNode(5);
        root1.right = new TreeNode(3);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);
    
        System.out.println(getTreeDepthWithRecur(root1));
    }
    
    public static int getTreeDepthWithRecur(TreeNode root)
    {
        if (root == null)
            return 0;
        int leftTreeHigh = getTreeDepthWithRecur(root.left);
        int rightTreeHigh = getTreeDepthWithRecur(root.right);
        return leftTreeHigh > rightTreeHigh ? leftTreeHigh+1 : rightTreeHigh+1;
    }
}
