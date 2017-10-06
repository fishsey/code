package _learn.shuati.temp;

import java.util.*;

public class Main2
{
    static class Node implements Comparable<Node>
    {
        int win = 0;
        int loss = 0;
        int abs = 0;
        int score = 0;
        String name;

        public Node(String name)
        {
            this.name = name;
        }

        @Override
        public int compareTo(Node other)
        {
            abs = win - loss;
            other.abs = other.win - other.loss;
            if (this.score == other.score)
            {
                if (this.abs == other.abs)
                {
                    return Integer.compare(this.win, other.win);
                }else
                {
                    return Integer.compare(this.abs, other.abs);
                }
            }else
            {
                return Integer.compare(this.score, other.score);
            }
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "win=" + win +
                    ", loss=" + loss +
                    ", abs=" + abs +
                    ", score=" + score +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    public static void main(String[] args)
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            HashMap<String, Node> maps = new HashMap<>();
            int n = Integer.parseInt(cin.nextLine().trim());
            for (int i=0; i<n; i++)
            {
                String name = cin.nextLine().trim();
                maps.put(name, new Node(name));
            }
            for (int i=1; i<=(n-1)*n/2; i++)
            {
                String[] result = cin.nextLine().trim().split("[ ]+");
                String[] names = result[0].split("-");
                String[] scores = result[1].split(":");
                maps.get(names[0]).win += Integer.parseInt(scores[0]);
                maps.get(names[0]).loss += Integer.parseInt(scores[1]);
                maps.get(names[1]).win += Integer.parseInt(scores[1]);
                maps.get(names[1]).loss += Integer.parseInt(scores[0]);
                if (Integer.parseInt(scores[0]) == Integer.parseInt(scores[1]))
                {
                    maps.get(names[0]).score += 1;
                    maps.get(names[1]).score += 1;
                }else if (Integer.parseInt(scores[0]) > Integer.parseInt(scores[1]))
                {
                    maps.get(names[0]).score += 3;
                }else
                {
                    maps.get(names[1]).score += 3;
                }
            }
            getResult(maps);
        }
    }

    private static void getResult(HashMap<String, Node> maps)
    {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Node> alst = new ArrayList<>(maps.values());
        Collections.sort(alst);
        for (int i=0; i<alst.size(); i++)

        //for (int i=alst.size()/2; i<alst.size(); i++)
        {
            System.out.println(alst.get(i));
            result.add(alst.get(i).name);
        }

        Collections.sort(result);
        result.forEach(System.out::println);

    }
}
