package _learn.shuati.P2016XIaoZhao;

import java.util.PriorityQueue;

/**
 * Created by root on 8/16/17.
 */
public class QunaerFindCoder
{
    static class  Node implements Comparable<Node>
    {
        int count;//the number of "coder"
        int index;//the index in  origin String[]
        String value;

        public Node(int count, int index, String value)
        {
            this.count = count;
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Node node)
        {
            return Integer.compare(node.count, this.count) == 0 ? Integer.compare(this.index, node.index) : Integer.compare(node.count, this.count);
        }
    }
    public static String[] findCoder(String[] A, int n)
    {
        int count;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < A.length; i++)
        {
            count = findCount(A[i], "coder");
            pq.add(new Node(count, i, A[i]));
        }
        for (int i = 0; i < A.length; i++)
        {
            A[i] = pq.poll().value;
        }
        return A;
    }

    private static  int findCount(String s, String coder)
    {
        //keep "" in head and tail in String[]
        return s.split(coder, -1).length - 1;
    }

    public static void main(String args[])
    {
    }
}
