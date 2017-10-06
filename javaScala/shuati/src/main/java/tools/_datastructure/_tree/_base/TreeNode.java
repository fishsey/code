package tools._datastructure._tree._base;

import java.io.Serializable;

/**
 * Created by root on 1/16/17.
 */
public class TreeNode<E extends Comparable<E>> implements Serializable
{
    public E element;
    public TreeNode<E> left;
    public TreeNode<E> right;
    public TreeNode<E> next;

    public TreeNode(E e)
    {
        element = e;
    }

    @Override
    public String toString()
    {
        return "TreeNode{" +
                "element=" + element +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
