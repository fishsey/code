package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/1/25.
 */
public class _MirrorTree
{

    public void Mirror(TreeNode root)
    {
        if(root == null)
            return;
        preOrder(root);
    }

    public void preOrder(TreeNode root)
    {
        if (root!= null)
        {
            //switch left and right
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
        }
        if (root.left != null)
            preOrder(root.left);

        if (root.right != null)
            preOrder(root.right);
    }
}
