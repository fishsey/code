package _learn.shuati.leetcode;

/**
 * Created by fishsey on 2017/9/10.
 */
public class P10_RegularExpressionMatching
{
    public boolean isMatch(String s, String p)
    {
        if (p.length() == 0)
            return s.length() == 0;
        else if (s.length() == 0)
        {
            if (p.length() > 1 && p.charAt(1) == '*')//?*subString
                return isMatch(s, p.substring(2));
            else
                return false;
        } else if (p.length() > 1 && p.charAt(1) == '*')
        {
            if (isMatch(s, p.substring(2))) //0个
                return true;
            else if (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.')//1个或者多个
            {
                return isMatch(s.substring(1), p);
            } else
            {
                return false;
            }
        } else
        {
            return (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.') && isMatch(s.substring(1), p.substring(1));
        }
    }
}
