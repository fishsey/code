package tools._datastructure._list._base;

import java.util.*;

public class _SkipList
{
    static class SkipListNode
    {
        public InnerNode key;
        public SkipListNode up, down, left, right;

        public SkipListNode(InnerNode k)
        {
            this.key = k;
            up = down = left = right = null;
        }

        public void setUp(SkipListNode up)
        {
            this.up = up;
        }
        public SkipListNode getUp()
        {
            return up;
        }

        public void setDown(SkipListNode down)
        {
            this.down = down;
        }
        public SkipListNode getDown()
        {
            return down;
        }

        public void setLeft(SkipListNode left)
        {
            this.left = left;
        }
        public SkipListNode getLeft()
        {
            return left;
        }

        public void setRight(SkipListNode right)
        {
            this.right = right;
        }
        public SkipListNode getRight()
        {
            return right;
        }

        public void setKey(InnerNode k)
        {
            this.key = k;
        }
        public InnerNode getKey()
        {
            return key;
        }
    }

    static class InnerNode<T> implements Comparable<InnerNode<T>>
    {
        T value;

        public InnerNode(T value)
        {
            this.value = value;
        }

        @Override
        public int compareTo(InnerNode o)
        {
            return ((Comparable)this.value).compareTo(((Comparable)o.value));
        }

        @Override
        public String toString()
        {
            //return "InnerNode{" +
            //        "value=" + value +
            //        '}';

            return  value.toString();
        }
    }

    public SkipListNode head;   //头节点  
    public SkipListNode tail;   //尾结点  
    public int h;   //层数  
    public int size;    //元素个数  
    public Random rand; //每次的随机数用来确定需不需要增加层数  

    public _SkipList()
    {
        head = new SkipListNode(null);
        tail = new SkipListNode(null);
        head.setRight(tail);
        tail.setLeft(head);
        h = 0;
        size = 0;
        rand = new Random();
    }

    public boolean isEmpty()
    {
        if (size == 0)
        {
            return true;
        }
        return false;
    }

    //找到需要插入位置的前一个节点  
    public SkipListNode findF(InnerNode k)
    {
        SkipListNode temp;
        temp = head;
        while (true)
        {
            while (temp.getRight().key != null && temp.getRight().key.compareTo(k) <= 0)
            {  
                temp = temp.getRight();
            }
            if (temp.getDown() != null)
            {
                temp = temp.getDown();
            } else
            {
                break;
            }
        }
        return temp;    //找到节点并返回  

    }

    public InnerNode add(InnerNode k)
    {
        SkipListNode temp, temp1;
        temp = findF(k);
        int i;  //当前层数  
        if (k.equals(temp.getKey()))
        {
            //System.out.println("对象属性完全相同无法添加！");
            return k;
        }
        temp1 = new SkipListNode(k);
        temp1.setLeft(temp);
        temp1.setRight(temp.getRight());
        temp.getRight().setLeft(temp1);
        temp.setRight(temp1);
        i = 0;

        while (rand.nextDouble() < 0.5)
        {    //进行随机，是否需要 在上层添加  
            if (i >= h)
            {    //若当前层数超出了高度，则需要另建一层  
                SkipListNode p1, p2;
                h = h + 1;
                p1 = new SkipListNode(null);
                p2 = new SkipListNode(null);

                p1.setRight(p2);
                p1.setDown(head);

                p2.setLeft(p1);
                p2.setDown(tail);

                head.setUp(p1);
                tail.setUp(p2);

                head = p1;
                tail = p2;
            }
            while (temp.getUp() == null)
            {
                temp = temp.getLeft();
            }
            temp = temp.getUp();
            SkipListNode node = new SkipListNode(k);
            node.setLeft(temp);
            node.setRight(temp.getRight());
            node.setDown(temp1);

            temp.getRight().setLeft(node);
            temp.setRight(node);
            temp1.setUp(node);

            temp1 = node;
            i = i + 1;

        }
        size = size + 1;
        return null;
    }

    //节点查找  
    public SkipListNode find(InnerNode k)
    {
        SkipListNode temp = head;
        SkipListNode node;
        node = temp;

        while (temp != null)
        {
            System.out.print(node.getKey() + "\t");
            while (node.getRight().key != null && node.getRight().getKey().compareTo(k) <= 0)
            {
                node = node.getRight();
                System.out.print(node.getKey() + "\t");
            }
            if (node.getDown() != null)
            {
                node = node.getDown();
                System.out.println();
            } else
            {
                if (node.key.equals(k))
                {
                    return node;
                }
                return null;
            }
        }
        return null;
    }

    //调用查找函数，删除最底层的某个节点，并把其节点的左右相连，和链表操作一样，只是其上方若有则都需要调整
    public void delSkipListNode(InnerNode k)
    {
        SkipListNode temp = find(k);
        while (temp != null)
        {
            temp.getLeft().setRight(temp.getRight());
            temp.getRight().setLeft(temp.getLeft());
            temp = temp.getUp();
        }
    }

    public void print()
    {
        SkipListNode node;
        SkipListNode node1 = head;

        while (node1 != null)
        {
            node = node1;
            while (node != null)
            {
                System.out.print(node.getKey() + "\t");
                node = node.getRight();
            }
            node1 = node1.getDown();
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        _SkipList s = new _SkipList();
        int i = 0;
        for (; i < 30; i++)
        {
            s.add(new InnerNode(i+""));
        }
        s.print();
        System.out.println();
        s.find(new InnerNode(2+""));
    }

}

