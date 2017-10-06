package _learn.shuati.leetcode;

import org.junit.Test;
import tools._datastructure._list._base.ListNode;

/**
 * Created by fishsey on 2017/9/19.
 */
public class P142_LinkedListCycleII
{
    public ListNode detectCycle(ListNode head)
    {
        if (head == null || head.next == null)
        {
            return null;
        }
        ListNode fast = head.next.next, slow = head.next;

        while (fast != slow )
        {
            if (fast == null || fast.next == null)
            {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        //定位入口
        slow = head;
        while (fast != slow)
        {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    
    @Test
    public void test_()
    {
         ListNode head = new ListNode(1);
         head.next = new ListNode(2);
         head.next.next = new ListNode(3);
         head.next.next.next = head.next;
    
        System.out.println(detectCycle(head));
    }
}
