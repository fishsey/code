package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

/**
 * Created by fishsey on 2017/8/29.
 * https://www.nowcoder.com/practice/532d89889b974506a0805062fd1089fb?tpId=49&tqId=29307&rp=2&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */
public class JinDongApple
{
    @Test
    public void test_()
    {
        System.out.println(getInitial(3));
    }
    
    public int getInitial(int n)
    {
        for (int i=n+1; ; i++)
        {
            int temp = i;
            int tempn = n;
            while (temp % n == 1 && tempn-- > 0)
            {
                temp = (temp-1)/n * (n-1);
            }
            if (tempn == 0)
                return i;
        }
    }

}
