package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by root on 2/11/17.
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
 * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
 */
public class _BstToDoubleLink
{
    TreeNode head = null;
    TreeNode tail = null;


    public TreeNode Convert(TreeNode root)
    {
        if (root == null)
            return null;
        else
        {
            ConvertWithInOrder(root);
            return head;
        }
    }

    private void ConvertWithInOrder(TreeNode root)
    {
        if (root == null)
            return;

        ConvertWithInOrder(root.left);

        if (head == null)//leftest node(smallest node)
        {
            head = root;
            tail = root;
        } else
        {
            tail.right = root; //tail is the previous node of root by inOrder traverse
            root.left = tail;
            tail = root;
        }
    
        ConvertWithInOrder(root.right);

    }
}
