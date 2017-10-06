package tools._datastructure._tree._util;


import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/9/14.
 * https://leetcode.com/problems/validate-binary-search-tree/description/
 */
public class _VerifyBinarySearchTree
{
    public boolean isValidBST(TreeNode root)
    {
        if (root == null)
            return true;
        if (!dfsLeft(root.left, root.val) || !dfsRight(root.right, root.val))
            return false;
        return isValidBST(root.left) && isValidBST(root.right);
    }

    public boolean dfsLeft(TreeNode root, int value)
    {
        if (root == null) return true;
        if (root.val >= value)
            return false;
        return dfsLeft(root.left, value) && dfsLeft(root.right, value);
    }

    public boolean dfsRight(TreeNode root, int value)
    {
        if (root == null)
            return true;
        if (root.val <= value)
            return false;
        return dfsRight(root.left, value) && dfsRight(root.right, value);
    }
}
