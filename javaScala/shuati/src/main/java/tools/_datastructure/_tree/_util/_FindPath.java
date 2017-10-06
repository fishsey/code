package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by fishsey on 2017/1/27.
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 */
public class _FindPath
{
    public static void main(String args[])
    {
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right = new TreeNode(3);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);


        TreeNode root2 = new TreeNode(7);

        System.out.println(new _FindPath().FindPath(root2, 7));
    }


    //solve without recur, by dfs
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target)
    {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        Stack<TreeNode> path = new Stack<>();

        if (root != null)
        {
            path.push(root);
            target -= (int)root.element;
        }

        TreeNode currentNode, parrentNode;
        int flag = 0;
        while (!path.empty())
        {
            if (flag == 0)
            {
                currentNode = path.peek();
                if (currentNode.left != null)
                {
                    path.push(currentNode.left);
                    target -= (int)currentNode.left.element;
                } else if (currentNode.right != null)
                {
                    path.push(currentNode.right);
                    target -= (int)currentNode.right.element;
                } else//leaf node
                {
                    if (target == 0)
                        paths.add(transToArray(path));
                    flag = 1;
                }
            } else if (flag == 1) //backtrace
            {
                currentNode = path.pop();
                target += (int)currentNode.element;

                if (!path.empty())
                    parrentNode = path.peek();
                else
                    break;
                //backtrace util back form left tree and right tree is not null
                while (parrentNode.right == null || parrentNode.right == currentNode)
                {
                    currentNode = path.pop();
                    target += (int)currentNode.element;
                    if (!path.empty())
                        parrentNode = path.peek();
                    else
                        break;
                }
                //push right tree
                if (!path.empty())
                {
                    path.push(parrentNode.right);
                    target -= (int)parrentNode.right.element;
                    flag = 0;
                }
            }
        }
        return paths;
    }

    private ArrayList<Integer> transToArray(Stack<TreeNode> path)
    {
        ArrayList<Integer> result = new ArrayList<>();
        for (TreeNode treeNode : path)
        {
            result.add((int)treeNode.element);
        }
        return result;
    }


    //solve with recur, by dfs
    private ArrayList<ArrayList<Integer>> listAll = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Integer> list = new ArrayList<Integer>();
    public ArrayList<ArrayList<Integer>> FindPathWithRcur(TreeNode root, int target)
    {
        if (root == null) return listAll;
        list.add((int)root.element);
        target -= (int)root.element;
        if (target == 0 && root.left == null && root.right == null)
            listAll.add(new ArrayList<Integer>(list));
        FindPath(root.left, target);
        FindPath(root.right, target);
        list.remove(list.size() - 1);
        return listAll;
    }
}
