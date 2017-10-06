package tools._datastructure._list._base;

/**
<<<<<<< HEAD
 * Created by fishsey on 2017/3/17.
 */
public class ListNode
{
    public int val;
    public ListNode next = null;
    public ListNode previous = null;

    public ListNode(int val)
    {
        this.val = val;
    }

    @Override
    public String toString()
    {
        return "ListNode{" +
                "val=" + val +
                '}';
    }

    public static ListNode getSingleLink()
    {
        ListNode pHead = new ListNode(1);
        pHead.next = new ListNode(2);
        pHead.next.next = new ListNode(3);
        pHead.next.next.next = new ListNode(3);
        pHead.next.next.next.next = new ListNode(4);
        pHead.next.next.next.next.next = new ListNode(5);
        return pHead;
    }
}
