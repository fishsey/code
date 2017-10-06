package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

import java.util.ArrayList;

/**
 * Created by fishsey on 2017/1/25.
 */
public class _SubTree
{
    //put all treenode in tree which treenode == root in result
    public void preTraverseWithRecur(TreeNode tree, TreeNode root, ArrayList<TreeNode> result)
    {

        if (tree != null && tree.element == root.element)
            result.add(tree);
        if (tree.left != null)
            preTraverseWithRecur(tree.left, root, result);
        if (tree.right != null)
            preTraverseWithRecur(tree.right, root, result);
    }

    //输入两棵二叉树 root1,root2; 判断 root2 是不是 root1 的子结构。
    //我们约定空树不是任意一个树的子结构
    public boolean isSubtree(TreeNode root1, TreeNode root2)
    {
        if (root2 == null || root1 == null)
            return false;

        //find all the nodes in root1 which equals root2
        ArrayList<TreeNode> result = new ArrayList<TreeNode>();
        preTraverseWithRecur(root1, root2, result);

        //traverse all treenode equals root2.root
        for (TreeNode node : result)
        {
            if(preTraverseCheckWithRecur(node, root2))
                return true;
        }
        return false;
    }

    //check if is subTree
    public boolean preTraverseCheckWithRecur(TreeNode root1, TreeNode root2)
    {
        //here, root2 is not null
        if (root1 == null || root2.element != root1.element)
            return false;

        if (root2.left != null)
            return preTraverseCheckWithRecur(root1.left, root2.left);

        if (root2.right != null)
            return preTraverseCheckWithRecur(root1.right, root2.right);

        return true;
    }
}
