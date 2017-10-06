package _learn.shuati.leetcode;

import org.junit.Test;
import tools._datastructure._tree._base.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fishsey on 2017/9/19.
 */
public class P236_LowestCommonAncestor
{
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)
    {
        HashMap<TreeNode, TreeNode> parent = new HashMap<>();
        if (root == null || p == null || q == null)
            return null;
        parent.put(root, null);
        //更新父节点信息
        updateParents(root, parent);
        //获取共同祖先
        TreeNode ances = getCommonAncestor(parent, p, q);
        return ances;
    }

    private TreeNode getCommonAncestor(HashMap<TreeNode, TreeNode> parent, TreeNode p, TreeNode q)
    {
        ArrayList<TreeNode> pathP = getPath(parent, p);
        ArrayList<TreeNode> pathQ = getPath(parent, q);
        int minLens = Math.min(pathP.size(), pathQ.size());

        TreeNode result = null;
        for (int i=1; i<=minLens; i++)
        {
            TreeNode pTemp = pathP.get(pathP.size()-i);
            TreeNode qTemp = pathQ.get(pathQ.size()-i);
            if (pTemp == qTemp)
            {
                result = pTemp;
            }else
                break;
        }
        return result;
    }

    private ArrayList<TreeNode> getPath(HashMap<TreeNode, TreeNode> parent, TreeNode p)
    {
        ArrayList<TreeNode> path = new ArrayList<>();
        path.add(p);
        while (parent.get(p) != null)
        {
            path.add(parent.get(p));
            p = parent.get(p);
        }
        return path;
    }

    private void updateParents(TreeNode root, HashMap<TreeNode, TreeNode> parent)
    {
        if (root.left != null)
        {
            parent.put(root.left, root);
            updateParents(root.left, parent);
        }
        if (root.right != null)
        {
            parent.put(root.right, root);
            updateParents(root.right, parent);
        }
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
