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
            return  true;
        int depthLeftTree = 0;
        int depthRightTree = 0;
        try
        {
            depthLeftTree = getDepth(root.left);
            depthRightTree = getDepth(root.right);
        } catch (RuntimeException e)
        {
            if (e.getMessage().equals("not balance"))
                return false;
            else
                throw e;
        }
        if (Math.abs(depthLeftTree-depthRightTree) > 1)
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
            //System.out.println(left + " " + right);
            throw new RuntimeException("not balance");
        }
        return left > right ? left+1: right+1;
    }
    
}
