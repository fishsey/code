package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by root on 2/21/17.
 */
public class _IsBalancedTree
{
    public static boolean isBalancedTree(TreeNode root)
    {
        if (root == null)
            return true;
        int depthLeft = 0;
        int depthRight = 0;
        try
        {
            depthLeft = getDepth(root.left);
            depthRight = getDepth(root.right);
        } catch (RuntimeException e)
        {
            if (e.getMessage().equals("not balance"))
                return false;
            else
                throw e;
        }
        if (Math.abs(depthLeft - depthRight) > 1)
            return false;
        return true;
        
    }
    
    private static int getDepth(TreeNode root)
    {
        if (root == null)
            return 0;
        int left = getDepth(root.left);
        int right = getDepth(root.right);
        if (Math.abs(left - right) > 1)
        {
            throw new RuntimeException("not balance");
        }
        return left > right ? left + 1 : right + 1;
    }
    
}
