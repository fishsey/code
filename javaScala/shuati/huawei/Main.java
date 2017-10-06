package _learn.shuati.huawei;


import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * Created by root on 6/19/17.
 */
public class Main
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int lineNum = cin.nextInt();
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        String numStr = new Integer(lineNum).toString();
        for (int i = numStr.length()-1; i >= 0; i--)
        {
            if (!set.contains(numStr.charAt(i)))
            {
                set.add(numStr.charAt(i));
            }
        }
        StringBuffer sb = new StringBuffer();
        for (Character character : set)
        {
            sb.append(character);
        }
        System.out.println(sb.toString());
    }


}
