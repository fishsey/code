package _learn.shuati.P2016XIaoZhao;

import java.util.*;

/**
 * Created by fishsey on 2017/9/2.
 */
public class ShouhuHuoYanJinJing
{
    static class Node
    {
        HashSet<Integer> ans = new HashSet<>();

        public Node()
        {
        }

        public Node(Node n1, Node n2)
        {
            for (Integer an : n1.ans)
            {
                ans.add(an);
            }
            for (Integer an : n2.ans)
            {
                ans.add(an);
            }
        }
    }

    static class CheatNode
    {
        int id1;
        int id2;

        public CheatNode(int id1, int id2)
        {
            this.id1 = Math.min(id1, id2);
            this.id2 = Math.max(id1, id2);
        }

        @Override
        public boolean equals(Object obj)
        {
            CheatNode node = (CheatNode) obj;
            return (this.id1 + " " + this.id2).equals(this.id1 + " " + this.id2);
        }

        @Override
        public int hashCode()
        {
            return (this.id1 + " " + this.id2).hashCode();
        }
    }

    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        HashMap<Integer,Node> map = new HashMap<>();
        while (cin.hasNext())
        {
            int n = cin.nextInt();
            while (n-- > 0)
            {
                int id = cin.nextInt();
                int ansNum = cin.nextInt();
                Node temp = new Node();
                while (ansNum-- > 0)
                {
                    temp.ans.add(cin.nextInt());
                }
                if (map.containsKey(id))
                {
                    Node old = map.get(id);
                    map.put(id, new Node(old, temp));
                }else
                {
                    map.put(id, temp);
                }
            }
            getResult(map);
            map.clear();

        }
    }

    private static void getResult(HashMap<Integer,Node> map)
    {
        //System.out.println(map);
        HashSet<Integer> cheatSet = new HashSet<>();
        HashSet<CheatNode> cheat = new HashSet<>();
        for (Map.Entry<Integer, Node> integerNodeEntry : map.entrySet())
        {
            int id = integerNodeEntry.getKey();
            Node temp = integerNodeEntry.getValue();
            for (Integer ansId : temp.ans)
            {

                if (ansId!=id && map.containsKey(ansId) && map.get(ansId).ans.contains(id))
                {
                    cheat.add(new CheatNode(id, ansId));
                    cheatSet.add(id);
                    cheatSet.add(ansId);
                }
            }
        }

        for (Map.Entry<Integer, Node> integerNodeEntry : map.entrySet())
        {
            int id = integerNodeEntry.getKey();
            Node temp = integerNodeEntry.getValue();
            if (cheatSet.contains(id))
                continue;
            for (CheatNode cheatNode : cheat)
            {
                if (temp.ans.contains(cheatNode.id1) && temp.ans.contains(cheatNode.id2))
                    cheatSet.add(id);
            }
        }


        ArrayList<Integer> result = new ArrayList<>(cheatSet);
        Collections.sort(result);

        System.out.println(cheatSet.size());
        if (cheatSet.size() > 0)
        {
            for (int i = 0; i < result.size()-1; i++)
            {
                System.out.print(result.get(i) + " ");
            }
            System.out.println(result.get(result.size()-1));
        }

    }
}
