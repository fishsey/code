package tools._math;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by root on 2/11/17.
 */
public class Combination<T>
{
  @Test
  public void test_()
  {
      Integer[] nums = {-1, 0, 1, 2, -1, -4};
      combination(nums).forEach(System.out::println);
  }

    /*
    * 返回所有元素的可能组合
    * */
    public static <T> ArrayList<ArrayList<T>> combination(T[] s)
    {
        if (s.length < 1)
            return null;
        ArrayList<ArrayList<T>> allCombs = new ArrayList<>();
        int lens = s.length;
        int n = 1<<lens; //n=2^lens
        //if s = "a","b","c", i=3(011), then get "a""b"
        for (int i=1; i<=n-1; i++)
        {
            ArrayList<T> temp = new ArrayList<>();
            // 011 & 001 == 001, get
            // 011 $ 010 == 010, get
            // 011 & 100 == 000, not get
            //取 i 中位为 1 的元素
            for (int j=0; j<=lens-1; j++)
            {
                if ((i & (1<<j)) != 0)
                {
                    temp.add(s[j]);
                }
            }
            allCombs.add(temp);
        }
        return allCombs;
    }
    
   
}
