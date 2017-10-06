package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

import java.util.Stack;

/**
 * Created by root on 2/17/17.
 */
public class _TreeDepth
{
    public static void main(String args[])
    {
    
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.left.right.left = new TreeNode(5);
        root1.left.right.left.left = new TreeNode(5);
        root1.right = new TreeNode(3);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);
    
        System.out.println(getTreeDepth(root1));
        System.out.println(getTreeDepthWithRecur(root1));
    
    }
    
    public static int getTreeDepthWithRecur(TreeNode root)
    {
        if (root == null)
            return 0;
        int leftTreeHigh = getTreeDepthWithRecur(root.left);
        int rightTreeHigh = getTreeDepthWithRecur(root.right);
        return leftTreeHigh > rightTreeHigh ? leftTreeHigh+1 : rightTreeHigh+1;
    }
    
    public static int getTreeDepth(TreeNode root)
    {
        if (root == null)
            return  0;
        else
            return treeDepthWithDFS(root);
    }
    
    private static int treeDepthWithDFS(TreeNode root)
    {
        Stack<TreeNode> path = new Stack<>();
        path.push(root);
        TreeNode currentNode, parrentNode;
        int flag = 0;
        int count = -1;
        while (!path.empty())
        {
            if (flag == 0)//down
            {
                currentNode = path.peek();//get top-elem, but not remove
                if (currentNode.left != null)
                {
                    path.push(currentNode.left);
                } else if (currentNode.right != null)
                {
                    path.push(currentNode.right);
                } else//leaf node
                {
                    flag = 1;
                    count = count >= path.size() ? count : path.size();//count depth of the leaf-node
                }
            }
            else if (flag == 1)//backup
            {
                currentNode = path.pop();//get and remove the top-elem
            
                if (!path.empty())
                    parrentNode = path.peek();
                else
                    break;//means the current node is root
                //if not backup from left node, then backup again
                while (parrentNode.right == null || parrentNode.right == currentNode)
                {
                    currentNode = path.pop();
                    if (!path.empty())
                        parrentNode = path.peek();
                    else
                        break;
                }
                //put the right-child into path and reset flag=0
                if (!path.empty())
                {
                    path.push(parrentNode.right);
                    flag = 0;
                }
            }
        }
        
        return count;
        
    }
}
