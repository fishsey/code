package _learn.shuati.P2016XIaoZhao;


/**
 * Created by root on 1/10/17.
 */
public class xiaoMiBinary
{
    public static void main(String args[])
    {
        int m = 1999, n= 2299;
        System.out.println(countBitDiff(m, n));
        System.out.println(Integer.toBinaryString(m));
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(m ^ n));
    }
	
	/**
     * 获得两个整形二进制表达位数不同的数量
     * 
     * @param m 整数m
     * @param n 整数n
     * @return 整型
     */
    public static int countBitDiff(int m, int n) 
    {
        int xor = m ^ n;//translate to count the number of 1 in xor
        int temp;
        int count_1 = 0, count_0=0;
        while (xor != 0)
        {
            temp = xor << 1;
            //if temp become bigger, then the leftmost is 0, else 1
            if (temp > xor)
            {
                count_0 += 1;
            }
            else if (temp < xor)
            {
                count_1 += 1;
            }
            xor = temp;
        }
        return count_1;
    }
}
