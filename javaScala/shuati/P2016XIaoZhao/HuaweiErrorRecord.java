package _learn.shuati.P2016XIaoZhao;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by root on 7/24/17.
 */
public class HuaweiErrorRecord
{
    static class ErrorNode implements Comparable<ErrorNode>
    {
        String partFileName;
        int count;
        String fileName;
        int lineNum;
        int index;

        public ErrorNode(String fileName, int lineNum, int index)
        {
            String[] parts = fileName.split("\\\\");
            this.fileName = parts[parts.length-1];
            this.partFileName = this.fileName.length() > 16 ? this.fileName.substring(this.fileName.length()-16, this.fileName.length()): this.fileName;
            this.lineNum = lineNum;
            this.count = 1;
            this.index = index;
        }

        public String getKey()
        {
            return this.fileName + this.lineNum;
        }

        @Override
        public int compareTo(ErrorNode obj)
        {
            if (this.count == obj.count)
            {
                return Integer.compare(this.index, obj.index);
            }
            return -1 * Integer.compare(this.count, obj.count);
        }

        @Override
        public String toString()
        {
            return this.partFileName + " " + this.lineNum + " " + this.count;
        }
    }



    public static void main(String args[])
    {
        HashMap<String, ErrorNode> errors = new HashMap<>();
        Scanner cin = new Scanner(System.in);

        int index = 1;
        while (cin.hasNext() )
        {
            String[] error = cin.nextLine().trim().split(" ");
            ErrorNode node = new ErrorNode(error[0], Integer.parseInt(error[1]), index++);

            if (errors.containsKey(node.getKey()))
            {
                errors.get(node.getKey()).count++;
            }
            else
            {
                errors.put(node.getKey(), node);
            }
        }

        PriorityQueue pq = new PriorityQueue(errors.values());
        int outputNum = 8;
        while (!pq.isEmpty() && outputNum-- > 0)
        {
            System.out.println(pq.poll());
        }
    }
}
