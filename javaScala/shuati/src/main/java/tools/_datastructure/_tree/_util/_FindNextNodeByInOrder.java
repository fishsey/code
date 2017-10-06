package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/3/17.
 * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
 * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
 */
public class _FindNextNodeByInOrder
{

    public TreeNode getNext(TreeNode pNode)
    {
        if (pNode == null)
            return pNode;
        else if (pNode.right != null)
        {
            //leftest node is the next node
            pNode = pNode.right;
            while (pNode.left != null)
            {
                pNode = pNode.left;
            }
            return pNode;
        }

        //right child-tree is null, then back up the parent
        TreeNode proot = pNode.next; //the parent of pNode
        while (proot != null)
        {
            //two case:
            //1:proot.left = pNode, so proot is the next node of inorder
            //2:proot.right=pNode, so the proot is the next node when proot.next.left=proot
            if (proot.left == pNode)
                return proot;
            pNode = proot;
            proot = pNode.next;
        }
        return null;//pNode is the rightest node in the tree
    }

}
