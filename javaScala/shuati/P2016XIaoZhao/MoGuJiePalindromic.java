package _learn.shuati.P2016XIaoZhao;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by fishsey on 2017/8/25.
 */
public class MoGuJiePalindromic
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        ArrayList<Character> lst = new ArrayList<>();
        String lineStr = null;
        while (cin.hasNext())
        {
            lineStr = cin.nextLine();
            transToLst(lineStr, lst);
            System.out.println(isCan(lst));
            lst.clear();
        }
    }

    private static String isCan(ArrayList<Character> lst)
    {
        int len = lst.size();
        ArrayList<Character> cloneLst = null;
        for (int i = 0; i <= len; i++)
        {
            cloneLst = (ArrayList<Character>) lst.clone();
            cloneLst.add(i, 'x');
            int pairPos = cloneLst.size() - i - 1;
            if (i == pairPos)
                cloneLst.remove(i);
            else
            {
                cloneLst.remove(i>pairPos?i:pairPos);
                cloneLst.remove(i>pairPos?pairPos:i);
            }
            int left=0, right=cloneLst.size()-1;
            int flag = 0;
            while (left < right)
            {
                if (cloneLst.get(left++)!=cloneLst.get(right--))
                {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0)
                return "YES";
        }
        return "NO";
    }

    private static void transToLst(String lineStr, ArrayList<Character> lst)
    {
        for (char c : lineStr.toCharArray())
        {
            lst.add(c);
        }
    }
}
