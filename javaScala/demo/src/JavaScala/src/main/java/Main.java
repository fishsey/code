import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by fishsey on 2017/9/24.
 */
public class Main
{
    static class Node implements Comparable<Node>
    {
        int start, end;

        public Node(int start, int end)
        {

            this.start = Math.min(start, end);
            this.end = Math.max(start, end);
        }

        @Override
        public int compareTo(Node o)
        {
            if (this.start == o.start)
            {
                return Integer.compare(this.end, o.end);

            }else
                return Integer.compare(this.start, o.start);
        }
    }

    public int putCatalyst(int numOfLines, List<Integer> startXPoints, List<Integer> endXPoints)
    {
        // WRITE YOUR CODE HERE
        if (numOfLines < 2)
            return numOfLines;

        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < numOfLines; ++i)
        {
            nodes.add(new Node(startXPoints.get(i), endXPoints.get(i)));
        }
        Collections.sort(nodes);

        int sum = 1;
        int end = nodes.get(0).end;

        for (int i = 1; i < numOfLines; ++i)
        {
            if (nodes.get(i).start > end)
            {
                sum++;
                end = nodes.get(i).end;
            }
        }
        return sum;
    }

    @Test
    public void test_()
    {
        List<Integer> a = Arrays.asList(0, 2, 4, -8);
        List<Integer> b = Arrays.asList(4, 5, 6, -9);
        System.out.println(putCatalyst(4, a, b));
    }

}
