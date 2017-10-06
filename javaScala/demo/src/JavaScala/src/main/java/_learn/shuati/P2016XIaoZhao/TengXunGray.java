package _learn.shuati.P2016XIaoZhao;

/**
 * Created by fishsey on 2017/8/31.
 * https://www.nowcoder.com/practice/50959b5325c94079a391538c04267e15?tpId=49&tqId=29310&tPage=3&rp=3&ru=/ta/2016test&qru=/ta/2016test/question-ranking
 */
public class TengXunGray
{
    public String[] getGray(int n)
    {
        if (n == 1)
        {
            String[] result = new String[2];
            result[0] = "0";
            result[1] = "1";
            return result;
        } else
        {
            String[] temp = getGray(n - 1);
            String[] result = new String[temp.length * 2];
            for (int i = 0; i < temp.length; i++)
            {
                result[i] = "0" + temp[i];
            }
            for (int i = 0; i < temp.length; i++)
            {
                result[i + temp.length] = "1" + temp[temp.length - i - 1];
            }
            return result;
        }
    }
}


