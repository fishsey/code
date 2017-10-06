package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/3/20.
 * 请实现一个函数，用来判断一颗二叉树是不是对称的。
 * 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
 */
public class _IsSymmetricalBinaryTree
{

    static boolean isSymmetrical(TreeNode pRoot)
    {
        if (pRoot == null)
            return false;
        else
            return isSymmetrical(pRoot.left, pRoot.right);
    }

    static boolean isSymmetrical(TreeNode left, TreeNode right)
    {
        if (left == null  && right == null) //both are null
            return true;
        if (left == null  || right == null)//one and only one is null
            return false;
        return left.element == right.element && isSymmetrical(left.left, right.right) && isSymmetrical(left.right, right.left);
    }
}
