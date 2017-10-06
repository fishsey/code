package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishsey on 2017/9/14.
 */
public class _VerifyBinarySearchTreeInOrder
{
    List<Integer> list = new ArrayList<Integer>();

    public boolean isValidBST(TreeNode root)
    {
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        inOrderTraversal(root);
        for (int i = 1; i < list.size(); i++)
        {
            if (list.get(i) <= list.get(i - 1)) return false;
        }
        return true;
    }

    public void inOrderTraversal(TreeNode root)
    {
        if (root == null) return;
        inOrderTraversal(root.left);
        list.add(root.val);
        inOrderTraversal(root.right);
    }
}
