package tools._usage;

import java.util.Random;

/**
 * 生成测试数据
 * Created by fishsey on 2017/4/24.
 */
public class _DataUtils
{
    public static int[] getIntArray(int nums)
    {
        Random random = new Random();
        int[] numArray = new int[nums];
        for (int i = 0; i < nums; i++)
        {
            numArray[i] = random.nextInt(10);
        }
        return numArray;
    }
}
