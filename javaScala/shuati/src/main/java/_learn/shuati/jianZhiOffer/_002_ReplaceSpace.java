package _learn.shuati.jianZhiOffer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 5/23/17.
 */
public class _002_ReplaceSpace
{
    public static void main(String args[])
    {
        StringBuffer stringBuffer = new StringBuffer(" we are happy ");
        System.out.println(replaceSpace(stringBuffer));
        System.out.println(replaceSpaceByRe(stringBuffer));

    }
    public static String replaceSpace(StringBuffer str)
    {
        String REPLACESTRING = "%20";
        //-1 means keep "" at head-And-tail
    	String[] subs = str.toString().split(" ", -1);
        StringBuffer result = new StringBuffer();
        for (String sub : subs)
        {
            result.append(sub);
            result.append(REPLACESTRING);
        }
        return result.toString().substring(0, result.length()-REPLACESTRING.length());
    }

    public static String replaceSpaceByRe(StringBuffer str)
    {
        String REPLACESTRING = "%20";
        Matcher match = Pattern.compile(" ").matcher(str.toString());
        return match.replaceAll("%20");
    }
}
