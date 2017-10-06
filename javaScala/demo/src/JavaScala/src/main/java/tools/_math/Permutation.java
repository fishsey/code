package tools._math;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by root on 2/11/17.
 */
public class Permutation
{
   @Test
   public void test_()
   {
       int[] nums = {1, 2, 3};
       List<List<Integer>> result = permute(nums);
       System.out.println(result.size());
       result.stream()
               .forEach(System.out::println);
   }

    public static List<List<Integer>> permute(int[] nums)
    {
        List<Integer> alst = new ArrayList<>();
        for (int  num : nums)
        {
            alst.add(num);
        }
        return allRange(alst);
    }

    public static <T> List<List<T>> allRange(List<T> s)
    {
        if (s == null)
            return null;
        else
        {
            ArrayList<List<T>> allranges = new ArrayList<List<T>>();
            allRange(s, 0, s.size()-1, allranges);
            return allranges;
        }
    }
    
    private static  <T> void allRange(List<T> s, int start, int end, List<List<T>> allranges)
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
    
    private static <T> boolean isSwap(List<T>  s, int start, int end)
    {
        for (; start<end; start++)
        {
            if (s.get(start).equals(s.get(end)))
                return false;
        }
        return true;
    }
    
    private static <T> void swap(List<T>  s, int left, int right)
    {
        T temp = s.get(left);
        s.set(left, s.get(right));
        s.set(right, temp);
    }
}
