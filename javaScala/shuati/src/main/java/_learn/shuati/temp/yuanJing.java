package _learn.shuati.temp;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by root on 8/17/17.
 */
public class yuanJing
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            int num = Integer.parseInt(cin.nextLine());
            System.out.println(getRemind(num));
        }
    }

    static class  Node
    {
        int flag;
        ArrayList<Integer> index = new ArrayList<>();

        public Node(int initIndex)
        {
            this.index.add(initIndex);
        }
    }

    private static int getRemind(int num)
    {
        ArrayList<Node> initNodes = initNodes(num);
        while (initNodes.size() > 1)
        {
            removeNodes(initNodes);
        }
        return initNodes.get(0).index.get(0);
    }

    private static void removeNodes(ArrayList<Node> initNodes)
    {
        int count = 1;
        for (int i=0; i<initNodes.size(); i++)
        {
            Node currentNode = initNodes.get(i);

            if (isPow(currentNode.index.get(currentNode.index.size()-1)))
            {
                currentNode.flag = 1;
            }else
            {
                currentNode.index.add(count++);
            }
        }
        for (int i=0; i<initNodes.size(); i++)
        {
            Node currentNode = initNodes.get(i);

            if (currentNode.flag == 1)
            {
                initNodes.remove(i);
            }
        }
    }

    private static boolean isPow(Integer integer)
    {
        int num = integer;
        double squrtNum = Math.sqrt(num);
        return squrtNum == (int) squrtNum;
    }

    private static ArrayList<Node> initNodes(int num)
    {
        ArrayList<Node> initNodes = new ArrayList<>();
        for (int i = 1; i <= num; i++)
        {
            initNodes.add(new Node(i));
        }
        return initNodes;
    }
}
