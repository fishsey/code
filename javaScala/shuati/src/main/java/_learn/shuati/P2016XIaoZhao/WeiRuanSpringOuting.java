package _learn.shuati.P2016XIaoZhao;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by root on 8/22/17.
 * https://www.nowcoder.com/practice/84addf13322a42ad80da5fc89e784ea1?tpId=49&tqId=29236&tPage=1&rp=1&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */
public class WeiRuanSpringOuting
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            int n = cin.nextInt();
            int k = cin.nextInt();
            cin.nextLine();
            ArrayList<String> choices = new ArrayList<String>();
            while (n-- > 0)
            {
                choices.add(" " + cin.nextLine() + " ");
            }
            //按照班级顺序，每个小组进行投票
            System.out.println(getChoice(choices, k));
        }
    }

    private static String getChoice(ArrayList<String> choices, int k)
    {
        String compareChoice = " 0 ";
        //from the last selector
        for (int i = k; i >= 1 ; i--)
        {
            //System.out.println(choice);
            String currentChoice = " " + i + " ";
            compareChoice = vote(currentChoice, compareChoice, choices);
        }
        if (compareChoice.equals(" 0 "))
            return "otaku";
        return compareChoice.trim();
    }

    private static String vote(String currentChoice, String compareChoice, ArrayList<String> choices)
    {
        ArrayList<Integer> compareIndex = getZeroIndex(choices, compareChoice);
        int count = 0;
        int numOfVoter = choices.size();
        for (int i = 0; i < choices.size(); i++)
        {
            int indexOff = choices.get(i).indexOf(currentChoice);
            if (indexOff < compareIndex.get(i))
                count++;;
        }
        if (count > numOfVoter/2.0)
        {
            compareChoice = currentChoice;
        }
        return compareChoice;
    }

    private static ArrayList<Integer> getZeroIndex(ArrayList<String> choices,  String compareChoice)
    {
        ArrayList<Integer> zeroIndex = new ArrayList<Integer>();
        for (String choice : choices)
        {
            zeroIndex.add(choice.indexOf(compareChoice));
        }
        return zeroIndex;
    }

}
