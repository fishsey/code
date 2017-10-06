package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

import java.util.ArrayDeque;


/**
 * Created by root on 1/16/17.
 */
public class _Traverse
{
    /**
     * Inorder traversal from a subtree
     */
    public static void inorder(TreeNode root)
    {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }


    /**
     * Postorder traversal from a subtree
     */
    public static void postorder(TreeNode root)
    {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }


    /**
     * Preorder traversal from a subtree
     */
    public static void preorder(TreeNode root)
    {
        if (root == null) return;
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    /**
     * return the high of tree
     */
    public static int levelOrder(TreeNode root)
    {
        //FIFO queue
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);
        int highLevel = 0;

        //null is the flag of every-level
        queue.addLast(null);
        highLevel ++;

        //record the current node
        TreeNode temp;
        while (queue.size() > 1)
        {
            temp = queue.pollFirst();
            if (temp != null)
            {
                System.out.print(temp.element + " ");
            }
            else//end of a level
            {
                System.out.println();
                queue.addLast(null);
                highLevel ++;
                continue;
            }
            if (temp.left != null)
                queue.addLast(temp.left);
            if (temp.right != null)
                queue.addLast(temp.right);
        }
        return highLevel;
    }
}


