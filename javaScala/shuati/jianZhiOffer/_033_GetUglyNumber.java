package _learn.shuati.jianZhiOffer;

import java.util.ArrayList;


/**
 * Created by root on 2/15/17.
 * 把只包含因子2、3和5的数称作丑数（Ugly Number）
 * 例如6、8都是丑数，但14不是，因为它包含因子7。
 * 习惯上我们把1当做是第一个丑数。
 * 求按从小到大的顺序的第N个丑数。
 */
public class _033_GetUglyNumber
{
    public static void main(String args[])
    {
        System.out.println(getUglyNumber(7));
        
    }
    
    private static int getUglyNumber(int n)
    {
        if (n <= 3)
            return n;
        else
        {
            ArrayList<Integer> uglyList = new ArrayList<>();
            uglyList.add(0, 1);
            int t2=0, t3=0, t5=0;//the last elem-index of *2, *3 *5 in uglyList(the min previous used number)
            
            for (int index=1; index<n; index++)
            {
                uglyList.add(index, min(uglyList.get(t2)*2, uglyList.get(t3)*3, uglyList.get(t5)*5));
                
                int temp = uglyList.get(index);
                
                //avoid duplicate numbers  set in uglyList
                if (temp == uglyList.get(t2)*2)
                    t2++;
                if (temp == uglyList.get(t3)*3)
                    t3++;
                if (temp == uglyList.get(t5)*5)
                    t5++;
            }
            return uglyList.get(n-1);
        }
        
    }
    
    private static int min(int a, int b, int c)
    {
        return Math.min(a, Math.min(b, c));
    }
}
