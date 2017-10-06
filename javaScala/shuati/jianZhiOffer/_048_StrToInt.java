package _learn.shuati.jianZhiOffer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 2/27/17.
 */
public class _048_StrToInt
{
    public static void main(String args[])
    {
        System.out.println(StrToInt("-123ee45"));
    }

    
    //return the first number format: " -1234 44":->-1234
    private static int StrToInt(String s)
    {
        //忽略任意空格，可能存在的-或者+，连续数字，遇见非数字截断
        Matcher m = Pattern.compile("^[\\s]*([-+]?[\\d]+)").matcher(s);
        if (m.find())
        {
            try
            {
                return Integer.valueOf(m.group(1));
            } catch (NumberFormatException e)
            {
                if (m.group(1).charAt(0) == '-') return Integer.MIN_VALUE;
                else return Integer.MAX_VALUE;
            }
        } else return 0;
    }
}
