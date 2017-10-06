package _learn.shuati.P2016XIaoZhao;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by fishsey on 2017/9/2.
 */
public class TengXunHongbao
{
    public int getValue(int[] gifts, int n)
    {
        HashMap<Integer, Integer> map = new HashMap<>();
        Integer temp = null;
        int result = 0;
        for (int gift : gifts)
        {
            temp = map.get(gift);
            if (temp == null)
                temp = 1;
            else
                temp += 1;

            if (temp > (n >> 1))
            {
                result = gift;
                break;
            } else
                map.put(gift, temp);
        }
        return result;
    }

    //分段的思想，每一段保证只有第一个元素的出现次数在本段里面是可能超过一半
    public int getValueSeg(int[] gifts, int n)
    {
        int count = 0, temp = 0;
        for (int i = 0; i < n; i++)
        {
            if (count == 0)
            {
                temp = gifts[i];
                count = 1;
            } else
            {
                if (temp == gifts[i])
                    count++;
                else
                    count--;
            }
        }
        int size = 0;
        for (int i = 0; i < n; i++)
        {
            if (temp == gifts[i])
                size++;
        }
        return (size > n / 2) ? temp : 0;
    }

    @Test
    public void test_()
    {
        int[] gifts = {1, 2, 3, 2, 2};
        System.out.println(getValue(gifts, gifts.length));
    }
}
