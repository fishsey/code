package tools._math;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by root on 2/11/17.
 */
public class Permutation
{
    public static void main(String args[])
    {
        List<String> lst = Stream.of("2", "2", "3", "4").collect(Collectors.toList());

        List<List<String>> allranges = Permutation.allRange(lst);

        allranges.stream().forEach(System.out::println);
        System.out.println(allranges.size());
        
    }

    /*
    * s = Stream.of("1", "2", "3", "4").collect(Collectors.toList());
    * */
    public static List<List<String>> allRange(List<String> s)
    {
        
        if (s == null)
            return null;
        else
        {
            ArrayList<List<String>> allranges = new ArrayList<List<String>>();

            //all ranges
            allRange(s, 0, s.size()-1, allranges);

            return allranges;
        }
    }
    
    private static void allRange(List<String> s, int start, int end, List<List<String>> allranges)
    {
        if (start == end)
            allranges.add(s.stream().collect(Collectors.toList()));
        else
        {
            //i as the current position, exchange other-char with i-char
            for (int i = start; i <= end; i++)
            {
                //if exist s[start, i-1] == s[i] then not swap, else swap
                //exclude duplicate, for example "abb"
				//动态规划：f(5):‘11234’ 首位为 1，2，3，4 情况下剩余数字的 f(4)
                if (isSwap(s, start, i))
                {
                    swap(s, start, i);

                    //allrange for s[start+1, length-1]
                    allRange(s, start+1, end, allranges);

                    swap(s, start, i);
                }
                
            }
        }
    }
    
    private static boolean isSwap(List<String>  s, int start, int end)
    {
        for (; start<end; start++)
        {
            if (s.get(start).equals(s.get(end)))
                return false;
        }
        return true;
    }
    
    private static void swap(List<String>  s, int left, int right)
    {
        String temp = s.get(left);
        s.set(left, s.get(right));
        s.set(right, temp);
    }
}
