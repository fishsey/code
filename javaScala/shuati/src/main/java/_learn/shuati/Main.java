package _learn.shuati;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            String line =cin.nextLine().trim();
            System.out.println(getResult(line));

        }
    }

    //RL
    private static int getResult(String lines)
    {
        int flag = lines.length();
        String text = lines;
        while (true)
        {
            ArrayList<Character> alst = new ArrayList<>();
            for (int i = 0; i < text.length(); i++)
            {
                char c = text.charAt(i);
                if (c=='L' || c == 'X')
                {
                    if (alst.size() > 0 && (alst.get(alst.size()-1)=='R' || alst.get(alst.size()-1)=='X'))
                    {
                        alst.remove(alst.size()-1);
                        alst.add('X');
                    }
                    else
                    {
                        alst.add(c);
                    }
                }else
                {
                    alst.add(c);
                }
            }
            if (alst.size() == flag)
                break;
            else
            {
                flag = alst.size();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < alst.size(); i++)
                {
                    sb.append(alst.get(i));
                }
                //System.out.println(sb);
                text = sb.toString();
            }
            
        }
        return flag;
    }
}
