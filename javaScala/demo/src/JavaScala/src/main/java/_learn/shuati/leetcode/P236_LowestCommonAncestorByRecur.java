package _learn.shuati.leetcode;

import org.junit.Test;
import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/9/19.
 */
public class P236_LowestCommonAncestorByRecur
{
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)
    {
        //发现目标节点则通过返回值标记该子树发现了某个目标结点
        if (root == null || root == p || root == q)
            return root;

        //查看左子树中是否有目标结点，没有为null
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        if (left != null && left != p && left != q) return left;

        //查看右子树是否有目标节点，没有为null
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (right != null && right != p && right != q) return right;

        //都不为空，说明左右子树都有目标结点，则公共祖先就是本身
        if (left != null && right != null)
            return root;

        //如果发现了目标节点，则继续向上标记为该目标节点
        return left == null ? right : left;
    }

    @Test
    public void test_()
    {
        TreeNode root = new TreeNode(1);
        TreeNode p = new TreeNode(2);
        TreeNode q = new TreeNode(3);
        root.left = p;
        root.right = q;
        lowestCommonAncestor(root, p, q);

    }
}
