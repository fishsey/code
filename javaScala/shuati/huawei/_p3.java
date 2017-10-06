package _learn.shuati.huawei;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fishsey on 2017/6/17.
 */
public class _p3
{
    public static void main(String[] args)
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            String text = cin.nextLine();
            String result = hander(text);
            System.out.println(result);
        }



    }

    //quote / reverse / search / combine
    //(search (combine "1234567890" "abcdefgh" "1234567890") (reverse "dc")) cdefgh123456789
    private static String hander(String text)
    {
        String result = "\"\"";
        int flag = 0;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < text.length(); i++)
        {
            char temp = text.charAt(i);
            if (temp == '\"')
            {
                flag ++;
            }
            if (temp == ')' && flag % 2 == 0)
            {
                String express = getExpress(stack);
                result = handerExpress(express);
                pushStack(result, stack);
            }
            else
            {
                stack.push(temp);
            }
        }
        return result;

    }

    private static void pushStack(String result, Stack<Character> stack)
    {
        for (int i = 0; i < result.length(); i++)
        {
            stack.push(result.charAt(i));
        }
    }

    private static String handerExpress(String express)
    {
        String result = null;
        String op = express.trim().split("[ ]+")[0];

        ArrayList<String> ops = getops(express);

        if (op.equals("quote"))
        {
            result = ops.get(0);
        }else if (op.equals("reverse"))
        {
            result = new StringBuffer(ops.get(0)).reverse().toString();
        }else if (op.equals("search"))
        {
            String s1 = ops.get(0);
            String s2 = ops.get(1);
            int index = new StringBuffer(s1).indexOf(s2.subSequence(1,s2.length()-1).toString());
            if (index == -1)
            {
                result = "\"\"";
            }
            else
            {
                result = "\"" + s1.substring(index);
            }
        }else if (op.equals("combine"))
        {
            StringBuffer sb = new StringBuffer();
            sb.append("\"");
            for (int i = 0; i < ops.size(); i++)
            {
                sb.append(ops.get(i).substring(1, ops.get(i).length()-1));
            }
            sb.append("\"");
            result = sb.toString();
        }else
        {
            result = "\"\"";
        }
        return result;
    }


    private static ArrayList<String> getops(String express)
    {
        ArrayList<String> ops = new ArrayList<>();
        Pattern pattern =  Pattern.compile("(\"[^\"]+\")");
        Matcher matcher = pattern.matcher(express);
        while (matcher.find())
        {
            ops.add(matcher.group());
        }
        return ops;
    }


    private static String getExpress(Stack<Character> stack)
    {
        int flag = 1;
        StringBuffer sb = new StringBuffer();
        char temp;
        while (((temp = stack.pop()) != '(') ||  (flag % 2 == 0))
        {
            if (temp == '\"')
                flag++;
            sb.append(temp);
        }
        return sb.reverse().toString();
    }
}
