package tools._datastructure._tree._util;

import org.junit.Test;
import tools._datastructure._tree._base.TreeNode;

import java.util.ArrayList;

/**
 * Created by fishsey on 2017/1/27.
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 */
public class _FindPathRecur
{
    @Test
    public void test_()
    {
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right = new TreeNode(3);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);

        FindPathWithRcur(root1, 7);
        listAll.forEach(System.out::println);
    }

    //solve with recur, by dfs
    private ArrayList<ArrayList<Integer>> listAll = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Integer> list = new ArrayList<Integer>();

    public void FindPathWithRcur(TreeNode root, int target)
    {
        if (root == null)
            return;

        list.add((int)root.element);
        target -= (int)root.element;

        if (target == 0 && root.left == null && root.right == null)
            listAll.add(new ArrayList<Integer>(list));

        FindPathWithRcur(root.left, target);
        FindPathWithRcur(root.right, target);

        //backup and reset
        list.remove(list.size() - 1);
    }
}
