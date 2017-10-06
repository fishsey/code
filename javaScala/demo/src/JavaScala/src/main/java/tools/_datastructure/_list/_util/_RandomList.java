package tools._datastructure._list._util;

/**
 * Created by fishsey on 2017/1/31.
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
 * 返回结果为复制后复杂链表的 head。
 * （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 */
public class _RandomList
{
    public static void main(String args[])
    {
        RandomListNode r1 = new RandomListNode(1);
        RandomListNode r2 = new RandomListNode(2);
        RandomListNode r3 = new RandomListNode(3);
        r1.next = r2; r1.random = r3;
        r2.next = r3; r2.random = r1;
        r3.next = null; r3.random = r1;
    

        RandomListNode r = new _RandomList().Clone(r1);
        System.out.println();
        System.out.println(r.label);
        System.out.println(r.next.label + " " + r.random.label);
        r = r.next;
        System.out.println(r.label);
        System.out.println(r.next.label + " " + r.random.label);
        r = r.next;
        System.out.println(r.label);
        System.out.println(r.random.label);

    }


    static class RandomListNode
    {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label)
        {
            this.label = label;
        }
    }

    public RandomListNode Clone(RandomListNode pHead)
    {
        if (pHead == null)
            return null;

        //first step: copy every node and insert into after origin-node
        System.out.println("step 1");
        RandomListNode pHeadTemp = pHead, copyNode;
        while (pHeadTemp != null)
        {
            System.out.println(pHeadTemp.label);
            copyNode = new RandomListNode(pHeadTemp.label);
            //insert
            copyNode.next = pHeadTemp.next;
            pHeadTemp.next = copyNode;
            //update the pHeadTemp
            pHeadTemp = copyNode.next;
        }

        //second step: update the random pointer
        System.out.println("step 2");
        pHeadTemp = pHead;
        copyNode = pHeadTemp.next;
        while (pHeadTemp != null)
        {
            System.out.println(copyNode.label);
            //the node random pointer may be is null
            if (pHeadTemp.random != null)
                copyNode.random = pHeadTemp.random.next;
            pHeadTemp = copyNode.next;
            if (pHeadTemp != null)
                copyNode = pHeadTemp.next;
        }

        //third step: separate the origin link and copy link
        System.out.println("step 3");
        pHeadTemp = pHead;
        copyNode = pHeadTemp.next;
        RandomListNode copyHead = copyNode;
        while (copyNode.next != null)
        {
            System.out.println(copyNode.label);
            pHeadTemp.next = pHeadTemp.next.next;
            copyNode.next = copyNode.next.next;
            pHeadTemp = pHeadTemp.next;
            copyNode = copyNode.next;
        }
        pHeadTemp.next = null;//separate

        return copyHead;
    }


}
