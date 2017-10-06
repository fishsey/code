package _learn.shuati.jianZhiOffer;

/**
 * Created by root on 5/26/17.
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
 */
public class _011_NumberOf1
{
    public static void main(String args[])
    {
        System.out.println(numberOf1(15));
    }
	
	public static int numberOf1(int n)
    {
        String binaryStr =  Integer.toBinaryString(n);
        int numberCount = 0;
        for (char c : binaryStr.toCharArray())
        {
            if (c == '1')
                numberCount++;
        }
        return numberCount;
    }
}
